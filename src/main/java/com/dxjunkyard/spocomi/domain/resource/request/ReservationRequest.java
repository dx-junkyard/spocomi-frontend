package com.dxjunkyard.spocomi.domain.resource.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {
    private Long communityId;
    private Long facilityId;
    private Long equipmentId;
    private String date;
    private String startTime;
    private String endTime;
    private boolean recruitFriends;
}
