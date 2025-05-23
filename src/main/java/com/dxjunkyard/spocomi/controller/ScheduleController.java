package com.dxjunkyard.spocomi.controller;

import com.dxjunkyard.spocomi.api.client.ScheduleRestClient;
import com.dxjunkyard.spocomi.domain.dto.ReservationDTO;
import com.dxjunkyard.spocomi.domain.dto.ClosureScheduleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    @Value("${service-url}")
    private String serviceUrl;

    @Autowired
    private ScheduleRestClient scheduleRestClient;

    @GetMapping("/equipment/{equipmentId}/reservations")
    public ResponseEntity<List<ReservationDTO>> getEquipmentReservations(
            @PathVariable Long equipmentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @CookieValue(value = "_token", defaultValue = "") String token) {
        logger.info("get equipment reservations API");
        List<ReservationDTO> reservations = scheduleRestClient.getEquipmentReservations(
                equipmentId, startDate, endDate, token);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/equipment/{equipmentId}/closures")
    public ResponseEntity<List<ClosureScheduleDTO>> getEquipmentClosures(
            @PathVariable Long equipmentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @CookieValue(value = "_token", defaultValue = "") String token) {
        logger.info("get equipment closures API");
        List<ClosureScheduleDTO> closures = scheduleRestClient.getEquipmentClosures(
                equipmentId, startDate, endDate, token);
        return ResponseEntity.ok(closures);
    }

    @GetMapping("/facility/{facilityId}/reservations")
    public ResponseEntity<List<ReservationDTO>> getFacilityReservations(
            @PathVariable Long facilityId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @CookieValue(value = "_token", defaultValue = "") String token) {
        logger.info("get facility reservations API");
        List<ReservationDTO> reservations = scheduleRestClient.getFacilityReservations(
                facilityId, startDate, endDate, token);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/facility/{facilityId}/closures")
    public ResponseEntity<List<ClosureScheduleDTO>> getFacilityClosures(
            @PathVariable Long facilityId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @CookieValue(value = "_token", defaultValue = "") String token) {
        logger.info("get facility closures API");
        List<ClosureScheduleDTO> closures = scheduleRestClient.getFacilityClosures(
                facilityId, startDate, endDate, token);
        return ResponseEntity.ok(closures);
    }

}
