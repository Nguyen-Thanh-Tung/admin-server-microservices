package com.comit.services.employee.loging.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.message.Message;

public class CustomMessage implements Message {

    private final RequestSessionLog log;

    public CustomMessage(RequestSessionLog log) {
        this.log = log;
    }

    @Override
    public String getFormattedMessage() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(log);
        } catch (JsonProcessingException e) {
            CommonLogger.error("Write log error!", e);
            return null;
        }
    }

    @Override
    public String getFormat() {
        return log.toString();
    }

    @Override
    public Object[] getParameters() {
        return new Object[0];
    }

    @Override
    public Throwable getThrowable() {
        return null;
    }
}