package com.dxjunkyard.spocomi.api.client;

import com.dxjunkyard.spocomi.domain.resource.request.ReservationRequest;
import com.dxjunkyard.spocomi.domain.dto.ReservationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;


@Component
public class ReservationRestClient {
    private Logger logger = LoggerFactory.getLogger(ReservationRestClient.class);

    @Value("${backend-api.url}")
    private String backend_api_url;

    public String postReservationRegistration(String token, ReservationRequest request) {
        try {
            String url = backend_api_url + "/v1/api/equipment-reserve/new";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Bearer " + token);
            HttpEntity<ReservationRequest> requestEntity = new HttpEntity<>(request,headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate
                    .exchange(url , HttpMethod.POST, requestEntity, String.class);
            return response.getBody();
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return "NG";
        }
    }

    public String postCheckIn(String token, Long counterId, Long userId) {
        return postCheckAction(token, counterId, userId, "/v1/api/check-in");
    }

    public String postCheckOut(String token, Long counterId, Long userId) {
        return postCheckAction(token, counterId, userId, "/v1/api/check-out");
    }

    public List<ReservationDTO> getEquipmentReservations(String token, Long equipmentId,
                                                         LocalDate startDate, LocalDate endDate) {
        return getReservations(token, equipmentId, startDate, endDate, true);
    }

    public List<ReservationDTO> getFacilityReservations(String token, Long facilityId,
                                                        LocalDate startDate, LocalDate endDate) {
        return getReservations(token, facilityId, startDate, endDate, false);
    }

    private List<ReservationDTO> getReservations(String token, Long targetId,
                                                 LocalDate startDate, LocalDate endDate,
                                                 boolean isEquipment) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<?> entity = new HttpEntity<>(headers);

            String urlTemplate = isEquipment
                    ? "/v1/api/equipment/{id}/reservations"
                    : "/v1/api/facility/{id}/reservations";

            String url = UriComponentsBuilder
                    .fromHttpUrl(backend_api_url + urlTemplate)
                    .queryParam("startDate", startDate)
                    .queryParam("endDate", endDate)
                    .buildAndExpand(targetId)
                    .toUriString();

            ResponseEntity<List<ReservationDTO>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<ReservationDTO>>() {
                    }
            );
            return response.getBody();
        } catch (Exception e) {
            logger.info("RestClient error : {}", e.toString());
            return List.of();
        }
    }

    private String postCheckAction(String token, Long counterId, Long userId, String path) {
        try {
            String url = backend_api_url + path;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Bearer " + token);
            HttpEntity<String> requestEntity = new HttpEntity<>(
                    String.format("{\"counterId\":%d,\"userId\":%d}", counterId, userId),
                    headers
            );
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate
                    .exchange(url, HttpMethod.POST, requestEntity, String.class);
            return response.getBody();
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return "NG";
        }
    }

}

