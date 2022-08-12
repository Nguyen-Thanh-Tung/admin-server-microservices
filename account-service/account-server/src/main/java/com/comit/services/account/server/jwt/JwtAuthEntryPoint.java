package com.comit.services.account.server.jwt;

import com.comit.services.account.client.response.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e)
            throws IOException, ServletException {
        BaseResponse responseBase = new BaseResponse(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        writer.println(objectMapper.writeValueAsString(responseBase));
    }
}
