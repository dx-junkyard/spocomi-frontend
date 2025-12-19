package com.dxjunkyard.spocomi.domain.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    // 曜日（0-6）
    private int day;

    // 日付（"YYYY-MM-DD"形式）
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String date;

    // 開始時刻（"HH:mm"形式）
    @JsonFormat(pattern = "HH:mm")
    private String startTime;

    // 終了時刻（"HH:mm"形式）
    @JsonFormat(pattern = "HH:mm")
    private String endTime;

    // イベントテキスト（"予約"など）
    private String eventText;

    // 予約の状態: 0=未利用, 1=使用中, 2=返却済
    private Integer status;

    // 予約の識別子
    private Long reservationId;

    // 窓口カウンターID
    private Long counterId;

    // 予約したユーザーID
    private Long userId;
}
