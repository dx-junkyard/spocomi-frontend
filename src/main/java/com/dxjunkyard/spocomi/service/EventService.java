package com.dxjunkyard.spocomi.service;

import com.dxjunkyard.spocomi.api.client.EventRestClient;
import com.dxjunkyard.spocomi.api.client.LineLoginRestClient;
import com.dxjunkyard.spocomi.api.client.UserRestClient;
import com.dxjunkyard.spocomi.domain.resource.Event;
import com.dxjunkyard.spocomi.domain.resource.request.AddEventRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EventService {
    private Logger logger = LoggerFactory.getLogger(EventService.class);

    @Autowired
    private EventRestClient eventRestClient;

    public AddEventRequest addEvent(String token, Event event) {
        LocalDateTime event_start = event.getEventDate().atTime(event.getEventStartTime());
        LocalDateTime event_end = event.getEventDate().atTime(event.getEventEndTime());
        AddEventRequest request = AddEventRequest.builder()
                .communityId(event.getCommunityId())
                .eventStart(String.valueOf(event_start))
                .eventEnd(String.valueOf(event_end))
                .applicationStart(String.valueOf(event.getApplicationStart()))
                .applicationEnd(String.valueOf(event.getApplicationEnd()))
                .ownerId(event.getOwnerId())
                .recruitmentMessage(event.getRecruitmentMessage())
                .description(event.getDescription())
                .visibility(event.getVisibility())
                .status(event.getStatus()).build();

        eventRestClient.postEventRegistration(token, request);
        return request;
    }
}
