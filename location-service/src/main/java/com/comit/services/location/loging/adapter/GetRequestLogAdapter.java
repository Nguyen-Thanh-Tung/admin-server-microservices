package com.comit.services.location.loging.adapter;

import com.comit.services.location.loging.constant.Const;
import com.comit.services.location.loging.service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.DispatcherType;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Process request with method get or delete before
 */
public class GetRequestLogAdapter implements HandlerInterceptor {

    @Autowired
    LoggingService loggingService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {

        if (DispatcherType.REQUEST.name().equals(request.getDispatcherType().name())) {
            request.setAttribute(Const.HEADER_REQUEST_TIME, System.currentTimeMillis());
            request.setAttribute(Const.HEADER_REQUEST_ID, UUID.randomUUID().toString());
            loggingService.logRequest(null);
        }

        return true;
    }
}
