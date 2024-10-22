package com.dxjunkyard.spocomi.domain.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityHistory {
    private Long eventId;
    private String date;
    private String eventName;
}
