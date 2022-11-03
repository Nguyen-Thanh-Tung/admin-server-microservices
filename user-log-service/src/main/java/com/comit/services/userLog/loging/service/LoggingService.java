package com.comit.services.userLog.loging.service;

import com.comit.services.userLog.loging.constant.Const;
import com.comit.services.userLog.loging.model.RequestSessionLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
@Getter
@Setter
public class LoggingService {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    HttpServletResponse httpServletResponse;
    private Map<String, RequestSessionLog> sessionLogMap = new HashMap<>();
    private PriorityQueue<String> completeRequest = new PriorityQueue<>();

    public void logRequest(Object request) {
        String method = httpServletRequest.getMethod();
        String path = httpServletRequest.getRequestURI();
        Map<String, String> header = buildParametersMap(httpServletRequest);
        initNewLog(method, path, header, request);
    }

    public void logResponse(Object response) {
        Map<String, String> header = buildHeadersMap(httpServletResponse);
        int status = httpServletResponse.getStatus();
        finishLog(header, status, response);
    }

    private Map<String, String> buildParametersMap(HttpServletRequest httpServletRequest) {
        Map<String, String> resultMap = new HashMap<>();
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = httpServletRequest.getParameter(key);
            resultMap.put(key, value);
        }

        return resultMap;
    }

    private Map<String, String> buildHeadersMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    private Map<String, String> buildHeadersMap(HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();

        Collection<String> headerNames = response.getHeaderNames();
        for (String header : headerNames) {
            map.put(header, response.getHeader(header));
        }
        return map;
    }

    public void initNewLog(String method, String path, Map<String, String> requestHeader, Object request) {
        String sessionID = (String) httpServletRequest.getAttribute(Const.HEADER_REQUEST_ID);
        RequestSessionLog sessionLog = new RequestSessionLog();
        sessionLog.setSessionID(sessionID);
        sessionLog.setMethod(method);
        sessionLog.setPath(path);
        try {
            sessionLog.setRequestHeader(objectMapper.writeValueAsString(requestHeader));

            if (request != null) {
                String requestBody = objectMapper.writeValueAsString(request);
//                JsonObject bodyObj = new JsonParser().parse(requestBody).getAsJsonObject();
                sessionLog.setRequest(requestBody);
            } else {
                sessionLog.setRequest(null);
            }
        } catch (Exception ignore) {
            // Body not json object
        }
        sessionLogMap.put(sessionID, sessionLog);
    }

    public void addInfo(String method, String info) {
        String sessionID = (String) httpServletRequest.getAttribute(Const.HEADER_REQUEST_ID);
        if (sessionLogMap.containsKey(sessionID)) {
            RequestSessionLog sessionLog = sessionLogMap.get(sessionID);
            sessionLog.addInfo(method, info);
        }
    }

    public void finishLog(Map<String, String> responseHeader, int status, Object response) {
        String sessionID = (String) httpServletRequest.getAttribute(Const.HEADER_REQUEST_ID);
        if (sessionLogMap.containsKey(sessionID)) {
            RequestSessionLog sessionLog = sessionLogMap.get(sessionID);
            try {
                sessionLog.setResponseHeader(objectMapper.writeValueAsString(responseHeader));
                String responseBody = objectMapper.writeValueAsString(response);
//                JsonObject bodyObj = new JsonParser().parse(responseBody).getAsJsonObject();
                sessionLog.setResponse(responseBody);
            } catch (Exception ignore) {
            }
            sessionLog.setResponseTime(new Date());
            sessionLog.setStatus(status);
            sessionLog.setComplete(true);
            completeRequest.add(sessionID);
        }
    }
}
