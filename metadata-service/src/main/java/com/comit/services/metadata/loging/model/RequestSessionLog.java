package com.comit.services.metadata.loging.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
public class RequestSessionLog {
    @JsonProperty("session_id")
    private String sessionID;
    private boolean isComplete;
    private int status;
    @JsonProperty("request_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ", timezone = "Asia/Ho_Chi_Minh")
    private Date requestTime;
    @JsonProperty("response_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ", timezone = "Asia/Ho_Chi_Minh")
    private Date responseTime;
    private String method;
    private String path;
    @JsonProperty("request_header")
    private String requestHeader;
    @JsonProperty("request_body")
    private String request;
    @JsonProperty("response_header")
    private String responseHeader;
    private Object response;
    @JsonProperty("debug_info")
    private ArrayList<DebugMessage> debugInfo = new ArrayList<>();
    private String type;

    public RequestSessionLog() {
        status = -1;
        requestTime = new Date();
        isComplete = false;
    }

    public void addInfo(String method, String info) {
        debugInfo.add(new DebugMessage(method, info));
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    static class DebugMessage {
        private String method;
        private String info;
    }
}
