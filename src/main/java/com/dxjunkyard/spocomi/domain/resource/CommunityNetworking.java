package com.dxjunkyard.spocomi.domain.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityNetworking {
    private Long myCommunityId;
    private String myCommunityName;
    private List<Long> partnerCommunityId;
    private List<String> partnerCommunityName;
    private String expirationAt;
}
