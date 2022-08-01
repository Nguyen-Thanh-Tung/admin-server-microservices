package com.comit.services.camera.adapter;

import com.comit.services.camera.client.AccountClient;
import com.comit.services.camera.client.UserLogClient;
import com.comit.services.camera.client.request.UserLogRequest;
import com.comit.services.camera.client.response.UserResponse;
import com.comit.services.camera.controller.response.BaseResponse;
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
                if (((BaseResponse) o).getCode() == 1) {
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
        if (Objects.equals(method, "POST") && Objects.equals(requestURI, "/cameras")) {
                content = "Thêm camera";
        } else if (Objects.equals(method, "PUT") && validField(requestURI, "/cameras/[0-9]+")) {
                content = "Cập nhật thông tin camera";
        } else if (Objects.equals(method, "DELETE") && validField(requestURI, "/cameras/[0-9]+")) {
                content = "Xóa camera";
        }
        if (!content.equals("")) {
            UserResponse userResponse = accountClient.getCurrentUser(request.getHeader("token")).getBody();
            if (userResponse != null && userResponse.getCode() == 1) {
                userLogClient.saveUserLog(request.getHeader("token"), new UserLogRequest(userResponse.getUser().getId(), content, new Date())).getBody();
            }
        }
    }

    public boolean validField(String value, String patternString) {
        Pattern pattern = Pattern.compile("^" + patternString + "$");
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }
}
