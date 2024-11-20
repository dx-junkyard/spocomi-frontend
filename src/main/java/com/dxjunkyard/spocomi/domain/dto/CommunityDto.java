package com.dxjunkyard.spocomi.domain.dto;

import com.dxjunkyard.spocomi.domain.resource.Community;
import com.dxjunkyard.spocomi.domain.resource.response.CommunityPage;

public class CommunityDto {

    public static Community toCommunity(CommunityPage communityPage, String ownerId) {
        if (communityPage == null) {
            return null;
        }

        return Community.builder()
                .id(communityPage.getId())
                .ownerId(ownerId) // 必要に応じて引数などで取得
                .location(parseLocation(communityPage.getLocation())) // locationをLongに変換
                .name(communityPage.getName())
                .summaryMessage(communityPage.getSummaryMessage())
                .summaryPr(communityPage.getSummaryPr())
                .description(communityPage.getDescription())
                .profileImageUrl(communityPage.getProfileImageUrl())
                .memberCount(communityPage.getMemberCount())
                .visibility(communityPage.getVisibility())
                .build();
    }

    private static Long parseLocation(String location) {
        // locationが数値の場合はLongに変換
        try {
            return location != null ? Long.valueOf(location) : null;
        } catch (NumberFormatException e) {
            // locationが数値でない場合の処理
            return null;
        }
    }
}

