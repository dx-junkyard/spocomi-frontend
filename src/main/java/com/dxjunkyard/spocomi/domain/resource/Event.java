package com.dxjunkyard.spocomi.domain.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private Integer id;
    private String title;
    private Long communityId;
    private String ownerId;
    private String imagePath;
    private LocalDate eventDate;  // イベント実施日付
    private LocalTime eventStartTime;
    private LocalTime eventEndTime;
    private LocalDateTime applicationStart;
    private LocalDateTime applicationEnd;
    private String recruitmentMessage;
    private String description;
    private String location;
    private Integer visibility;
    private Integer status; // 0 : 募集期間前, 1 : 募集期間中, 2 : 募集期間終了, 3: イベント期間中, 4 : イベント終了
}

