package com.comit.services.userlog.client;

import com.comit.services.userlog.client.request.UserLogRequest;
import com.comit.services.userlog.client.response.BaseResponse;

public interface UserLogClient {
    BaseResponse saveUserLog(String token, UserLogRequest userLogRequest);
}
