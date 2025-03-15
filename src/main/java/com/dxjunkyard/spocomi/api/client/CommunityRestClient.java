package com.dxjunkyard.spocomi.api.client;

import com.dxjunkyard.spocomi.domain.resource.Community;
import com.dxjunkyard.spocomi.domain.resource.CommunityNetworking;
import com.dxjunkyard.spocomi.domain.resource.CommunitySummary;
import com.dxjunkyard.spocomi.domain.resource.request.FavoriteRequest;
import com.dxjunkyard.spocomi.domain.resource.response.CommunityMemberList;
import com.dxjunkyard.spocomi.domain.resource.response.CommunityPage;
import com.dxjunkyard.spocomi.domain.resource.response.MyPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.net.URLDecoder;

@Component
public class CommunityRestClient {
    private Logger logger = LoggerFactory.getLogger(CommunityRestClient.class);

    @Value("${backend-api.url}")
    private String backend_api_url;

    public List<CommunitySummary> getCommunityKeywordSearchApi(String token, String keyword) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            String decodedKeyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8.name());
            String urlTemplate = UriComponentsBuilder.fromHttpUrl(backend_api_url + "/v1/api/communities/keyword-search")
                    .queryParam("keyword", decodedKeyword)
                    .toUriString();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<List<CommunitySummary>> response = restTemplate.exchange(
                    urlTemplate,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<CommunitySummary>>() {
                    }
            );
            List<CommunitySummary> summaryList = response.getBody();
            return summaryList;

        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return new ArrayList<>();
        } catch (Exception e) {
            logger.info("error : {}", e.toString());
            return new ArrayList<>();
        }
    }
    public List<CommunitySummary> getCommunityListApi(String token) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            String url = backend_api_url + "/v1/api/communities/communitylist";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<List<CommunitySummary>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
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
            String url = backend_api_url + "/v1/api/communities/" + community_id.toString() + "/page";
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

    public Long useInvitation(String token, String state) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            String url = backend_api_url + "/v1/api/communities/" + state + "/invitation";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<Long> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    Long.class);
            return response.getBody();

        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return null;
        }
    }

    public String createInvitationCode(String token, Long communityId, Long eventId, Integer maxUses) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            String url = backend_api_url + "/v1/api/communities/invitation/create";
            UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(url);
            if (communityId != null) {
                urlBuilder.queryParam("community_id", communityId.toString());
            }
            if (eventId != null) {
                urlBuilder.queryParam("event_id", eventId.toString());
            }
            if (maxUses != null) {
                urlBuilder.queryParam("max_uses", maxUses.toString());
            } else {
                urlBuilder.queryParam("max_uses", "1");
            }

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    urlBuilder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    String.class);
            return response.getBody();

        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return null;
        }
    }

    public Community  getCommunityRegistration(String token, Long community_id) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            String url = backend_api_url + "/v1/api/communities/" + community_id.toString() + "/registration";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<Community> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    Community.class);
            return response.getBody();

        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return new Community();
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

    public Community postEditCommunity(String token, Community community) {
        try {
            String url = backend_api_url + "/v1/api/communities/community/edit";
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

    public Long createGroup(String token, CommunityNetworking request) {
        try {
            String url = backend_api_url + "/v1/api/communities/community/create-group";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Bearer " + token);
            HttpEntity<CommunityNetworking> requestEntity = new HttpEntity<>(request,headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Long> response = restTemplate
                    .exchange(url , HttpMethod.POST, requestEntity, Long.class);
            return response.getBody();
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return -1L;
        }
    }

    public Community updateCommunity(String token, Community community) {
        try {
            String url = backend_api_url + "/v1/api/communities/community/update";
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

    public Integer updateFavoriteStatus(String token, FavoriteRequest request) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            String url = backend_api_url + "/v1/api/communities/favorite";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            HttpEntity<FavoriteRequest> entity = new HttpEntity<>(request, headers);
            ResponseEntity<Integer> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    Integer.class);
            return response.getBody();

        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return 0;
        }
    }

    public String getCommunityName(String token, Long community_id) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            String url = backend_api_url + "/v1/api/communities/" + community_id.toString() + "/community-name";
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

    public List<CommunityMemberList> getMyCommunityList(String token) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            String url = backend_api_url + "/v1/api/communities/my-community-list";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<List<CommunityMemberList>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<CommunityMemberList>>() {});
            return response.getBody();

        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return new ArrayList<CommunityMemberList>();
        }
    }

    public String uploadPhoto(String token, MultipartFile multipartFile) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            String url = backend_api_url + "/v1/api/communities/community/upload-image";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            // MultiValueMapにファイルを追加
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("photo", multipartFile.getResource());

            // リクエストエンティティ作成
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    String.class);

            String photoPath = backend_api_url + "/uploads/" + response.getBody();
            return photoPath;
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return "";
        }
    }
}
