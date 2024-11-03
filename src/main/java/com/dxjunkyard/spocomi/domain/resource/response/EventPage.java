package com.dxjunkyard.spocomi.domain.resource.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventPage {
    private Long id;
    private String title;  // イベント名
    private Long communityId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime eventStart;  // イベント開始時刻
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime eventEnd;  // イベント終了時刻
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime applicationStart; // 募集開始
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime applicationEnd;  // 募集終了
    private String recruitmentMessage;
    private String description;
    private Integer placeId;
    private Integer visibility;
    private Integer status;
}
