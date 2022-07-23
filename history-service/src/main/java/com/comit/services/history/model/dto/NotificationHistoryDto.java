package com.comit.services.history.model.dto;

import com.comit.services.history.model.entity.NotificationHistory;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Getter
@Setter
public class NotificationHistoryDto extends BaseModelDto {
    private String type;

    private Date time;
    private String status;

    public static NotificationHistoryDto convertNotificationHistoryToNotificationHistoryDto(NotificationHistory notificationHistory) {
        if (notificationHistory == null) return null;
        try {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(notificationHistory, NotificationHistoryDto.class);
        } catch (Exception e) {
            return null;
        }
    }
}
