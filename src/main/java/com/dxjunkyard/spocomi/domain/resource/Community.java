package com.dxjunkyard.spocomi.domain.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Community {
    private Long id;
    private String ownerId;
    private Long location;
    private String name;
    private String summaryMessage;
    private String summaryPr;
    private String description;
    private String profileImageUrl;
    private Integer memberCount;
    private Integer visibility;
}
