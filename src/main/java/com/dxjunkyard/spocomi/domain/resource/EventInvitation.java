package com.dxjunkyard.spocomi.domain.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventInvitation {
    private LocalDateTime dateTime;
    private String communityName;
    private String eventName;
    private String location;
    private String invitationCode;
}
