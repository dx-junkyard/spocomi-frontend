package com.dxjunkyard.spocomi.api.client;

import com.dxjunkyard.spocomi.domain.resource.Community;
import com.dxjunkyard.spocomi.domain.resource.CommunitySummary;
import com.dxjunkyard.spocomi.domain.resource.response.CommunityPage;
import com.dxjunkyard.spocomi.domain.resource.response.MyPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
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

    public CommunityPage getCommunityPage(String token, Long community_id) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            String url = backend_api_url + "/v1/api/communities/" + community_id.toString();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<CommunityPage> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    CommunityPage.class);
            return response.getBody();

        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return new CommunityPage();
        }
    }

    public Community postCommunityRegistration(String token, Community community) {
       try {
           String url = backend_api_url + "/v1/api/communities/community/new";
           HttpHeaders headers = new HttpHeaders();
           headers.setContentType(MediaType.APPLICATION_JSON);
           headers.add("Authorization", "Bearer " + token);
           HttpEntity<Community> requestEntity = new HttpEntity<>(community,headers);
           RestTemplate restTemplate = new RestTemplate();
           ResponseEntity<Community> response = restTemplate
                   .exchange(url , HttpMethod.POST, requestEntity, Community.class);
           return response.getBody();
       } catch (RestClientException e) {
           logger.info("RestClient error : {}", e.toString());
           return new Community();
       }
    }

    public MyPage getMyPage(String token) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            String url = backend_api_url + "/v1/api/communities/myhome";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<MyPage> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    MyPage.class);
            return response.getBody();

        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return new MyPage();
        }
    }
}
