package com.dxjunkyard.spocomi.api.client;

import com.dxjunkyard.spocomi.domain.resource.CommunitySummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommunityRestClient {
    private Logger logger = LoggerFactory.getLogger(CommunityRestClient.class);

    @Value("${backend-api.url}")
    private String backend_api_url;

    public List<CommunitySummary> getCommunityListApi() {
        RestTemplate restTemplate = new RestTemplate();

        try {
            String url = backend_api_url + "/v1/api/communities/communitylist";
            ResponseEntity<List<CommunitySummary>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<CommunitySummary>>() {
                    }
            );
            List<CommunitySummary> summaryList = response.getBody();
            return summaryList;

        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return new ArrayList<>();
        }
    }

}
