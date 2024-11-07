package com.dxjunkyard.spocomi.api.client;

import com.dxjunkyard.spocomi.domain.resource.Community;
import com.dxjunkyard.spocomi.domain.resource.CommunitySummary;
import com.dxjunkyard.spocomi.domain.resource.Event;
import com.dxjunkyard.spocomi.domain.resource.request.AddEventRequest;
import com.dxjunkyard.spocomi.domain.resource.response.EventPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class EventRestClient {
    private Logger logger = LoggerFactory.getLogger(EventRestClient.class);

    @Value("${backend-api.url}")
    private String backend_api_url;

    public List<EventPage> searchEventByKeywordApi(String keyword) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            String decodedKeyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8.name());
            String urlTemplate = UriComponentsBuilder.fromHttpUrl(backend_api_url + "/v1/api/events/keyword-search")
                    .queryParam("keyword", decodedKeyword)
                    .toUriString();
            ResponseEntity<List<EventPage>> response = restTemplate.exchange(
                    urlTemplate,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<EventPage>>() {
                    }
            );
            List<EventPage> eventList = response.getBody();
            return eventList;

        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return new ArrayList<>();
        } catch (Exception e) {
            logger.info("error : {}", e.toString());
            return new ArrayList<>();
        }
    }
    public List<EventPage> getEventListApi() {
        RestTemplate restTemplate = new RestTemplate();

        try {
            String url = backend_api_url + "/v1/api/events/eventlist";
            ResponseEntity<List<EventPage>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<EventPage>>() {
                    }
            );
            List<EventPage> summaryList = response.getBody();
            return summaryList;

        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return new ArrayList<>();
        }
    }

    public EventPage getEventPage(String token, Long event_id) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            String url = backend_api_url + "/v1/api/events/event/" + event_id.toString() + "/display";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<EventPage> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    EventPage.class);
            return response.getBody();

        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return new EventPage();
        }
    }

    public EventPage postEventRegistration(String token, EventPage event) {
        try {
            String url = backend_api_url + "/v1/api/events/event/new";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Bearer " + token);
            HttpEntity<EventPage> requestEntity = new HttpEntity<>(event,headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<EventPage> response = restTemplate
                    .exchange(url , HttpMethod.POST, requestEntity, EventPage.class);
            return response.getBody();
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return new EventPage();
        }
    }

}

