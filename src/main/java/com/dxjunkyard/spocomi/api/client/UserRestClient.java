package com.dxjunkyard.spocomi.api.client;

import com.dxjunkyard.spocomi.domain.resource.CommunitySummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserRestClient {
    private Logger logger = LoggerFactory.getLogger(CommunityRestClient.class);

    @Value("${backend-api.url}")
    private String backend_api_url;

    // lineIdからスポコミのuserId生成＆token生成を行う
    public String getTokenByLineId(String lineId) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            String url = backend_api_url + "/v1/api/users/register-lineid";
            RequestEntity<String> request = RequestEntity
                    .post(url)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(lineId);
            ResponseEntity<String> response = restTemplate.exchange(request, String.class);
            return response.getBody();

        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return "";
        }
    }


}
