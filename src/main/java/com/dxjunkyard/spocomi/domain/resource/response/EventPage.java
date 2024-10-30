package com.dxjunkyard.spocomi.domain.resource.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventPage {
    private String title;  // イベント名
    private Long communityId;
    private LocalDateTime eventDate;  // イベント実施日付
    private LocalDateTime eventStart;  // イベント開始時刻
    private LocalDateTime eventEnd;  // イベント終了時刻
    private LocalDateTime applicationStart; // 募集開始
    private LocalDateTime applicationEnd;  // 募集終了
    private String recruitmentMessage;
    private String description;
    private Integer placeId;
    private Integer visibility;
    private Integer status;
}
