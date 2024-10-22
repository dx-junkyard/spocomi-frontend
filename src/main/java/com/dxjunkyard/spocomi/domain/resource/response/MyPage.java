package com.dxjunkyard.spocomi.domain.resource.response;

import com.dxjunkyard.spocomi.domain.resource.CommunitySelector;
import com.dxjunkyard.spocomi.domain.resource.EventInvitation;
import com.dxjunkyard.spocomi.domain.resource.FavoriteCommunity;
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
public class MyPage {
    private List<CommunitySelector> communitySelectorList;
    private List<UpcommingEvent> upcommingEventList;
    private List<EventInvitation> eventInvitationList;
    private List<FavoriteCommunity> favoriteCommunityList;
}
