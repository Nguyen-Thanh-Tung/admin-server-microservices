package com.comit.services.account.adapter;

import com.comit.services.account.business.CommonBusiness;
import com.comit.services.account.client.UserLogClient;
import com.comit.services.account.client.request.UserLogRequestClient;
import com.comit.services.account.constant.Const;
import com.comit.services.account.controller.response.BaseResponse;
import com.comit.services.account.model.entity.User;
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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Order(0)
@ControllerAdvice
public class ResponseAdapter implements ResponseBodyAdvice<Object> {

    @Autowired
    UserLogClient userLogClient;
    @Autowired
    CommonBusiness commonBusiness;

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
            User currentUser = commonBusiness.getCurrentUser();
            if (currentUser != null) {
                userLogClient.saveUserLog(request.getHeader("token"), new UserLogRequestClient(currentUser.getId(), content, new Date())).getBody();
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
        lkMapApi.put(Const.USER_AR + "_" + Const.POST, Const.ADD_USER);
        lkMapApi.put(Const.USER_ID_AR + "_" + Const.PUT, Const.UPDATE_USER);
        lkMapApi.put(Const.USER_ID_AR + "_" + Const.DELETE, Const.DELETE_USER);
        lkMapApi.put(Const.UPDATE_ROLES_AR + "_" + Const.PUT, Const.UPDATE_ROLES);
        lkMapApi.put(Const.LOCK_ACCOUNT_AR + "_" + Const.PUT, Const.LOCK_ACCOUNT);
        lkMapApi.put(Const.UPDATE_AVATAR_AR + "_" + Const.PUT, Const.UPDATE_AVATAR);
        lkMapApi.put(Const.RESEND_CODE_AR + "_" + Const.POST, Const.RESEND_CODE);

        return lkMapApi;
    }
}
