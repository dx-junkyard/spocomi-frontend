package com.dxjunkyard.spocomi.controller;

import com.dxjunkyard.spocomi.api.client.CommunityRestClient;
import com.dxjunkyard.spocomi.domain.resource.request.FavoriteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/community")
public class Controller {

    @Autowired
    private CommunityRestClient communityRestClient;

    @PostMapping("/favorite")
    public ResponseEntity<Integer> toggleFavorite(@RequestBody FavoriteRequest favoriteRequest,
                                                  @CookieValue(value = "_token", defaultValue = "") String token) {

        // ここでデータベースの更新処理を行う（お気に入り状態の設定/解除）
        Integer updateSuccess = communityRestClient.updateFavoriteStatus(token, favoriteRequest);

        //boolean updateSuccess = updateFavoriteStatus(communityId, status);

        if (updateSuccess == 1) {
            // 更新後のステータスを返却
            return ResponseEntity.ok(favoriteRequest.getStatus());
        } else {
            return ResponseEntity.status(500).body(-1);
        }
    }

    private boolean updateFavoriteStatus(Long communityId, int status) {
        // 実際のデータベース更新処理を実装
        // 成功した場合にtrueを返すようにします
        return true;
    }
}
