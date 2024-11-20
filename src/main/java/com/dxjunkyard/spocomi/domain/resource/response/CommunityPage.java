package com.dxjunkyard.spocomi.domain.resource.response;

import com.dxjunkyard.spocomi.domain.resource.ActivityHistory;
import com.dxjunkyard.spocomi.domain.resource.EventSchedule;
import com.dxjunkyard.spocomi.domain.resource.UpcommingEvent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityPage {
    private Long id;
    private String location;
    private String name;
    private String summaryMessage;
    private String summaryPr;
    private String profileImageUrl;
    private String description;
    private Integer memberCount;
    private Integer visibility;
    private List<EventSchedule> eventScheduleList;
    private List<ActivityHistory> activityHistoryList;
}
