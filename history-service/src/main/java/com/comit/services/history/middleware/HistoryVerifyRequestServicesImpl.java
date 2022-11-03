package com.comit.services.history.middleware;

import com.comit.services.history.client.data.MetadataDtoClient;
import com.comit.services.history.constant.Const;
import com.comit.services.history.constant.HistoryErrorCode;
import com.comit.services.history.controller.request.InOutHistoryRequest;
import com.comit.services.history.exception.RestApiException;
import com.comit.services.history.service.HistoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Service
public class HistoryVerifyRequestServicesImpl implements HistoryVerifyRequestServices {

    @Autowired
    HistoryServices historyServices;
    @Override
    public void verifySaveHistory(InOutHistoryRequest request) {
        if (request.getImageId() == null) {
            throw new RestApiException(HistoryErrorCode.FAIL);
        } else {
            MetadataDtoClient metadataDtoClient = historyServices.getMetadata(request.getImageId());
            if (Objects.isNull(metadataDtoClient)) {
                throw new RestApiException(HistoryErrorCode.IMAGE_NOT_EXIST);
            }
        }

        String time = request.getTime();
        if (time == null || time.trim().equals("")) {
            throw new RestApiException(HistoryErrorCode.MISSING_TIME_FIELD);
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Const.DATE_FORMAT_COMMON);
            try {
                simpleDateFormat.parse(request.getTime());
            } catch (ParseException e) {
                throw new RestApiException(HistoryErrorCode.TIME_INVALID);
            }
        }

    }
}
