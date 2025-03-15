package com.dxjunkyard.spocomi.api.client;

import com.dxjunkyard.spocomi.domain.resource.request.ReservationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Component
public class ReservationRestClient {
    private Logger logger = LoggerFactory.getLogger(ReservationRestClient.class);

    @Value("${backend-api.url}")
    private String backend_api_url;

    public Integer postReservationRegistration(String token, ReservationRequest request) {
        try {
            String url = backend_api_url + "/v1/api/equipment-reserve/new";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Bearer " + token);
            HttpEntity<ReservationRequest> requestEntity = new HttpEntity<>(request,headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Integer> response = restTemplate
                    .exchange(url , HttpMethod.POST, requestEntity, Integer.class);
            return response.getBody();
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return 1;
        }
    }

}

