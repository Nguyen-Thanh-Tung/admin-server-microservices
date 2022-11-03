package com.comit.services.employee.service;


public interface KafkaServices {
    void sendMessage(String topic, String message);
}
