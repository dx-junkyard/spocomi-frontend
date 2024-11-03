package com.dxjunkyard.spocomi.service;

import com.dxjunkyard.spocomi.api.client.EventRestClient;
import com.dxjunkyard.spocomi.api.client.LineLoginRestClient;
import com.dxjunkyard.spocomi.api.client.UserRestClient;
import com.dxjunkyard.spocomi.domain.resource.Event;
import com.dxjunkyard.spocomi.domain.resource.request.AddEventRequest;
import com.dxjunkyard.spocomi.domain.resource.response.EventPage;
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

    public EventPage addEvent(String token, EventPage eventPage) {
        //LocalDateTime event_start = event.getEventDate().atTime(event.getEventStartTime());
        //LocalDateTime event_end = event.getEventDate().atTime(event.getEventEndTime());
        /*
        AddEventRequest request = AddEventRequest.builder()
                .title(event.getTitle())
                .communityId(event.getCommunityId())
                .eventStart(String.valueOf(event.getEventStart()))
                .eventEnd(String.valueOf(event.getEventEnd()))
                .applicationStart(String.valueOf(event.getApplicationStart()))
                .applicationEnd(String.valueOf(event.getApplicationEnd()))
                .recruitmentMessage(event.getRecruitmentMessage())
                .description(event.getDescription())
                .placeId(event.getPlaceId())
                .visibility(event.getVisibility())
                .status(event.getStatus()).build();
        */
        //return eventRestClient.postEventRegistration(token, request);
        return eventRestClient.postEventRegistration(token, eventPage);
    }
}
