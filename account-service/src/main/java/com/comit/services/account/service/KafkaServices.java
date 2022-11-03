package com.comit.services.account.service;


public interface KafkaServices {
    void sendMessage(String topic, String message);
}
