package com.dxjunkyard.spocomi.api.client;

import com.dxjunkyard.spocomi.domain.resource.response.MyPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

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

    public String getUserIdByToken(String token) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            String url = backend_api_url + "/v1/api/users/get-userid";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class);
            return response.getBody();

        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return "";
        }
    }

}
