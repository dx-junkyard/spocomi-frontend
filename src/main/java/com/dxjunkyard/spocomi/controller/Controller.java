package com.dxjunkyard.spocomi.controller;

import com.dxjunkyard.spocomi.api.client.CommunityRestClient;
import com.dxjunkyard.spocomi.api.client.ReservationRestClient;
import com.dxjunkyard.spocomi.api.client.UserRestClient;
import com.dxjunkyard.spocomi.domain.resource.CommunityNetworking;
import com.dxjunkyard.spocomi.domain.resource.request.FavoriteRequest;
import com.dxjunkyard.spocomi.domain.resource.request.ReservationRequest;
import com.dxjunkyard.spocomi.domain.resource.response.CommunityMemberList;
import com.dxjunkyard.spocomi.domain.resource.response.CommunityName;
import com.dxjunkyard.spocomi.service.CommunityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/v1/api/community")
public class Controller {
    private Logger logger = LoggerFactory.getLogger(Controller.class);

    @Value("${file.upload-dir}")
    private String UPLOAD_DIR;

    @Value("${service-url}")
    private String serviceUrl;

    @Autowired
    private CommunityRestClient communityRestClient;

    @Autowired
    private UserRestClient userRestClient;

    @Autowired
    private CommunityService communityService;

    @Autowired
    private ReservationRestClient reservationRestClient;

    @PostMapping("/favorite")
    public ResponseEntity<Integer> toggleFavorite(@RequestBody FavoriteRequest favoriteRequest,
                                                  @CookieValue(value = "_token", defaultValue = "") String token) {

        // ここでデータベースの更新処理を行う（お気に入り状態の設定/解除）
        Integer updateSuccess = communityRestClient.updateFavoriteStatus(token, favoriteRequest);

        if (updateSuccess == 1) {
            // 更新後のステータスを返却
            return ResponseEntity.ok(favoriteRequest.getStatus());
        } else {
            return ResponseEntity.status(500).body(-1);
        }
    }

    @GetMapping("/invitation")
    public ResponseEntity<String> createInvitation( @CookieValue(value = "_token", defaultValue = "") String token,
                                                    @RequestParam(value="community_id") Long communityId,
                                                    @RequestParam(value="event_id", required = false) Long eventId,
                                                    @RequestParam(value="max_uses", defaultValue = "1") Integer maxUses) {
        String invitationCode = communityRestClient.createInvitationCode(token, communityId, eventId, maxUses);
        String invitationUrl = serviceUrl + "/v1/user/line-login/?state=" + invitationCode;

        if (invitationCode != null) {
            // 更新後のステータスを返却
            return ResponseEntity.ok(invitationUrl);
        } else {
            return ResponseEntity.status(500).body("-1");
        }
    }

    @PostMapping("/create-group")
    public ResponseEntity<?> createGroup(@RequestBody CommunityNetworking request,
                                               @CookieValue(value = "_token", defaultValue = "") String token) {

        // ここでデータベースの更新処理を行う（お気に入り状態の設定/解除）
        Long newGroupId = communityRestClient.createGroup(token, request);

        if (newGroupId == -1L) {
            return ResponseEntity.status(500).body(-1);
        } else {
            // 更新後のステータスを返却
            return ResponseEntity.ok(newGroupId);
        }
    }


    @GetMapping("/{community_id}/community-name")
    public ResponseEntity<?> getCommunityName(
            @CookieValue(value="_token", required=false) String token,
            @PathVariable(value="community_id") Long communityId) {
        logger.info("get community-name API");
        String communityName= communityRestClient.getCommunityName(token, communityId);

        if (communityName.isEmpty()) {
            return ResponseEntity.status(500).body(-1);
        } else {
            CommunityName response = CommunityName.builder()
                    .communityName(communityName)
                    .build();
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/my-community-list")
    public ResponseEntity<?> getMyCommunityList(
            @CookieValue(value="_token", required=false) String token ) {
            logger.info("get my-community-list API");
        List<CommunityMemberList> communityMemberList = communityRestClient.getMyCommunityList(token);

        if (communityMemberList.isEmpty()) {
            return ResponseEntity.ok(new ArrayList<CommunityMemberList>());
        } else {
            return ResponseEntity.ok(communityMemberList);
        }
    }

    @PostMapping("/reservation/new")
    public ResponseEntity<?> equipmentReservation(
            @RequestBody ReservationRequest request,
            @CookieValue(value = "_token", defaultValue = "") String token) {

        String ret = reservationRestClient.postReservationRegistration(token,request);
        // ここでデータベースの更新処理を行う（お気に入り状態の設定/解除）
        //Long newGroupId = communityRestClient.createGroup(token, request);

        return ResponseEntity.ok(ret);
    }


    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(@RequestParam("imageUrl") MultipartFile file,
        @CookieValue(value = "_token", defaultValue = "") String token) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ファイルが選択されていません。");
        }

        try {
            // String filepath = communityRestClient.uploadPhoto(token, file);
            String myId = userRestClient.getUserIdByToken(token);
            String filepath = communityService.savePhoto(myId, file);
            // 保存パスを返却
            return ResponseEntity.ok(filepath);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("画像のアップロードに失敗しました。");
        }
    }
}
