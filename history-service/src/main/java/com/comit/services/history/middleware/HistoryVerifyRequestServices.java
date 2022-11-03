package com.comit.services.history.middleware;

import com.comit.services.history.controller.request.InOutHistoryRequest;

public interface HistoryVerifyRequestServices {
    void verifySaveHistory(InOutHistoryRequest inOutHistoryRequest);
}
