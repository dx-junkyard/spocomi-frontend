package com.dxjunkyard.spocomi.controller;

import com.dxjunkyard.spocomi.api.client.CommunityRestClient;
import com.dxjunkyard.spocomi.api.client.UserRestClient;
import com.dxjunkyard.spocomi.domain.resource.request.FavoriteRequest;
import com.dxjunkyard.spocomi.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/v1/api/community")
public class Controller {

    @Value("${file.upload-dir}")
    private String UPLOAD_DIR;

    @Autowired
    private CommunityRestClient communityRestClient;

    @Autowired
    private UserRestClient userRestClient;

    @Autowired
    private CommunityService communityService;

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
