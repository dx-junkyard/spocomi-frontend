package com.dxjunkyard.spocomi.domain.resource.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityMemberList {
    private String id;
    private String name;
    private List<String> memberList;
}
