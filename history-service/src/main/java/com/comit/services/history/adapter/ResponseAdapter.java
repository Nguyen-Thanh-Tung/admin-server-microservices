package com.comit.services.history.adapter;

import com.comit.services.history.client.AccountClient;
import com.comit.services.history.client.UserLogClient;
import com.comit.services.history.client.request.UserLogRequestClient;
import com.comit.services.history.client.response.UserResponseClient;
import com.comit.services.history.constant.Const;
import com.comit.services.history.controller.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Order(0)
@ControllerAdvice
public class ResponseAdapter implements ResponseBodyAdvice<Object> {

    @Autowired
    UserLogClient userLogClient;
    @Autowired
    AccountClient accountClient;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o instanceof BaseResponse) {
            try {
                HttpServletRequest request = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest();
                // If action is success, user log will be created
                if (((BaseResponse) o).getCode() == 1 && !Objects.equals(request.getHeader(Const.INTERNAL), Const.INTERNAL)) {
                    addUserLog(request);
                }
            } catch (Exception e) {
                return o;
            }
        }

        return o;
    }

    @Async
    public void addUserLog(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        String content = "";

        for (Map.Entry<String, String> hashMap : MapAPI().entrySet()) {
            if (validField(requestURI + "_" + method, hashMap.getKey())) {
                content = hashMap.getValue();
                break;
            }
        }
        if (!content.equals("")) {
            UserResponseClient userResponseClient = accountClient.getCurrentUser(request.getHeader("token")).getBody();
            if (userResponseClient != null && userResponseClient.getCode() == 1) {
                userLogClient.saveUserLog(request.getHeader("token"), new UserLogRequestClient(userResponseClient.getUser().getId(), content, new Date())).getBody();
            }
        }
    }

    public boolean validField(String value, String patternString) {
        Pattern pattern = Pattern.compile("^" + patternString + "$");
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }

    public static LinkedHashMap<String, String> MapAPI() {
        LinkedHashMap<String, String> lkMapApi = new LinkedHashMap<>(); // map<method + "_" + app route, message>
        lkMapApi.put(Const.IN_OUT_HISTORY_AR + "_" + Const.POST, Const.IN_OUT_HISTORY);
        lkMapApi.put(Const.NOTIFICATION_HISTORY_ID_AR + "_" + Const.PUT, Const.NOTIFICATION_HISTORY_ID);
        lkMapApi.put(Const.NOTIFICATION_HISTORY_AR + "_" + Const.POST, Const.NOTIFICATION_HISTORY);

        return lkMapApi;
    }
}
