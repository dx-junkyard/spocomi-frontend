package com.dxjunkyard.spocomi.domain.resource.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteRequest {
    // リクエストボディ用のクラス
    private Long communityId;
    private int status;
}
