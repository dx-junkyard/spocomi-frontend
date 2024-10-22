package com.dxjunkyard.spocomi.domain.resource.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddEventRequest {
    private Long communityId;

    @NotBlank(message = "Title cannot be empty")
    private String title;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$", message = "eventStart should be in 'yyyy-MM-dd HH:mm' format")
    private String eventStart;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$", message = "eventEnd should be in 'yyyy-MM-dd HH:mm' format")
    private String eventEnd;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$", message = "applicationStart should be in 'yyyy-MM-dd HH:mm' format")
    private String applicationStart;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$", message = "applicationEnd should be in 'yyyy-MM-dd HH:mm' format")
    private String applicationEnd;

    @Size(min = 3, max = 120, message = "OwnerId must be between 3 and 120 characters")
    private String ownerId;
    private Integer placeId;
    @Size(min = 1, max = 256, message = "OwnerId must be between 3 and 256 characters")
    private String recruitmentMessage;
    private String description;
    private Integer visibility;
    private Integer status;
}

