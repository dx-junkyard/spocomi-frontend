package com.dxjunkyard.spocomi.domain.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpcommingEvent {
    private Long eventId;
    private String communityName;
    private String date;
    private String eventName;
}
