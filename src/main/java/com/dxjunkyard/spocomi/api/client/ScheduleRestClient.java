package com.dxjunkyard.spocomi.api.client;

import com.dxjunkyard.spocomi.domain.dto.ClosureScheduleDTO;
import com.dxjunkyard.spocomi.domain.dto.ReservationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduleRestClient {

    @Value("${backend-api.url}")
    private String backendUrl;

    /**
     * 備品の予約情報を取得する
     *
     * @param equipmentId 備品ID
     * @param startDate 開始日
     * @param endDate 終了日
     * @param token 認証トークン
     * @return 予約情報のリスト
     */
    public List<ReservationDTO> getEquipmentReservations(
            Long equipmentId,
            LocalDate startDate,
            LocalDate endDate,
            String token) {

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<?> entity = new HttpEntity<>(headers);

            String url = UriComponentsBuilder
                    .fromHttpUrl(backendUrl + "/v1/api/equipment/{equipmentId}/reservations")
                    .queryParam("startDate", startDate)
                    .queryParam("endDate", endDate)
                    .buildAndExpand(equipmentId)
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
            throw new RuntimeException("Failed to get equipment reservations: " + e.getMessage(), e);
        }
    }

    /**
     * 備品の閉鎖スケジュールを取得する
     *
     * @param equipmentId 備品ID
     * @param startDate 開始日
     * @param endDate 終了日
     * @param token 認証トークン
     * @return 閉鎖スケジュールのリスト
     */
    public List<ClosureScheduleDTO> getEquipmentClosures(
            Long equipmentId,
            LocalDate startDate,
            LocalDate endDate,
            String token) {

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<?> entity = new HttpEntity<>(headers);

            String url = UriComponentsBuilder
                    .fromHttpUrl(backendUrl + "/v1/api/equipment/{equipmentId}/closures")
                    .queryParam("startDate", startDate)
                    .queryParam("endDate", endDate)
                    .buildAndExpand(equipmentId)
                    .toUriString();

            ResponseEntity<List<ClosureScheduleDTO>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<ClosureScheduleDTO>>() {
                    }
            );

            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get equipment closures: " + e.getMessage(), e);
        }
    }


    /**
     * 備品の予約情報を取得する
     *
     * @param facilityId 設備ID
     * @param startDate 開始日
     * @param endDate 終了日
     * @param token 認証トークン
     * @return 予約情報のリスト
     */
    public List<ReservationDTO> getFacilityReservations(
            Long facilityId,
            LocalDate startDate,
            LocalDate endDate,
            String token) {

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<?> entity = new HttpEntity<>(headers);

            String url = UriComponentsBuilder
                    .fromHttpUrl(backendUrl + "/v1/api/facility/{facilityId}/reservations")
                    .queryParam("startDate", startDate)
                    .queryParam("endDate", endDate)
                    .buildAndExpand(facilityId)
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
            throw new RuntimeException("Failed to get facility reservations: " + e.getMessage(), e);
        }
    }

    /**
     * 備品の閉鎖スケジュールを取得する
     *
     * @param facilityId 設備ID
     * @param startDate 開始日
     * @param endDate 終了日
     * @param token 認証トークン
     * @return 閉鎖スケジュールのリスト
     */
    public List<ClosureScheduleDTO> getFacilityClosures(
            Long facilityId,
            LocalDate startDate,
            LocalDate endDate,
            String token) {

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<?> entity = new HttpEntity<>(headers);

            String url = UriComponentsBuilder
                    .fromHttpUrl(backendUrl + "/v1/api/facility/{facilityId}/closures")
                    .queryParam("startDate", startDate)
                    .queryParam("endDate", endDate)
                    .buildAndExpand(facilityId)
                    .toUriString();

            ResponseEntity<List<ClosureScheduleDTO>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<ClosureScheduleDTO>>() {
                    }
            );

            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get facility closures: " + e.getMessage(), e);
        }
    }

}
