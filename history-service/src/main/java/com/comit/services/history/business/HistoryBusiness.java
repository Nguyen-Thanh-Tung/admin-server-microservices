package com.comit.services.history.business;

import com.comit.services.history.model.dto.FirstInLastOutHistoryDto;
import com.comit.services.history.model.dto.InOutHistoryDto;
import com.comit.services.history.model.dto.NotificationHistoryDto;
import com.comit.services.history.model.entity.FirstInLastOutHistory;
import com.comit.services.history.model.entity.InOutHistory;
import com.comit.services.history.model.entity.NotificationHistory;

public interface HistoryBusiness {
    InOutHistoryDto convertInOutHistoryToInOutHistoryDto(InOutHistory inOutHistory);

    FirstInLastOutHistoryDto convertInOutHistoryToInOutHistoryDto(FirstInLastOutHistory inOutHistory);

    NotificationHistoryDto convertNotificationHistoryToNotificationHistoryDto(NotificationHistory notificationHistory);
}
