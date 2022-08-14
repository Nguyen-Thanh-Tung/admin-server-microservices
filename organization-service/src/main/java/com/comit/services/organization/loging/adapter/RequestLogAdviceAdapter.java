package com.comit.services.organization.loging.adapter;

import com.comit.services.organization.loging.constant.Const;
import com.comit.services.organization.loging.service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.UUID;

@Order(0)
@ControllerAdvice
public class RequestLogAdviceAdapter extends RequestBodyAdviceAdapter {

    @Autowired
    LoggingService loggingService;
    @Autowired
    HttpServletRequest httpServletRequest;

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {
        httpServletRequest.setAttribute(Const.HEADER_REQUEST_TIME, System.currentTimeMillis());
        httpServletRequest.setAttribute(Const.HEADER_REQUEST_ID, UUID.randomUUID().toString());

        loggingService.logRequest(body);
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }
}
