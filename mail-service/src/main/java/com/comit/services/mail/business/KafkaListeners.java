package com.comit.services.mail.business;

import com.comit.services.mail.loging.model.CommonLogger;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaListeners {

    @Autowired
    private MailBusiness mailBusiness;

    @KafkaListener(topics = "createUser", groupId = "mail-service-group")
    public void confirmCreateUserMail(String message) {
        try {
            JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();
            if (jsonObject.has("id") && jsonObject.has("fullname") && jsonObject.has("email") && jsonObject.has("code")) {
                Integer id = jsonObject.get("id").getAsInt();
                String fullname = jsonObject.get("fullname").getAsString();
                String email = jsonObject.get("email").getAsString();
                String code = jsonObject.get("code").getAsString();

                boolean result = mailBusiness.sendConfirmCreateUserMail(id, fullname, email, code);
                if (result) {
                    CommonLogger.info("Send mail create user success for: " + email);
                } else {
                    CommonLogger.error("Send mail create user fail for: " + email);
                }
            } else {
                CommonLogger.error("Send mail create user fail format: " + message);
            }
        } catch (Exception e) {
            CommonLogger.error("Message not json format: " + message);
        }
    }

    @KafkaListener(topics = "forgetPassword", groupId = "mail-service-group")
    public void forgetPasswordMail(String message) {
        try {
            JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();
            if (jsonObject.has("id") && jsonObject.has("fullname") && jsonObject.has("email") && jsonObject.has("code")) {
                Integer id = jsonObject.get("id").getAsInt();
                String fullname = jsonObject.get("fullname").getAsString();
                String email = jsonObject.get("email").getAsString();
                String code = jsonObject.get("code").getAsString();
                boolean result = mailBusiness.sendForgetPasswordMail(id, fullname, email, code);
                if (result) {
                    CommonLogger.info("Send mail forget password success for: " + email);
                } else {
                    CommonLogger.error("Send mail forget password fail for: " + email);
                }
            } else {
                CommonLogger.error("Send mail create user fail format: " + message);
            }
        } catch (Exception e) {
            CommonLogger.error("Message not json format: " + message);
        }
    }

    @KafkaListener(topics = "qrCode", groupId = "mail-service-group")
    public void qrCodeMail(String message) {
        try {
            JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();
            if (jsonObject.has("email")
                    && jsonObject.has("fullname")
                    && jsonObject.has("employee_code")
                    && jsonObject.has("organization_name")
                    && jsonObject.has("location_name")
                    && jsonObject.has("type")
            ) {
                String email = jsonObject.get("email").getAsString();
                String fullname = jsonObject.get("fullname").getAsString();
                String employeeCode = jsonObject.get("employee_code").getAsString();
                String organizationName = jsonObject.get("organization_name").getAsString();
                String locationName = jsonObject.get("location_name").getAsString();
                String locationCode = jsonObject.get("location_code").getAsString();
                String type = jsonObject.get("type").getAsString();
                boolean result = mailBusiness.sendQrCodeMail(email, fullname, employeeCode, organizationName, locationName, locationCode, type);
                if (result) {
                    CommonLogger.info("Send mail forget password success for: " + email);
                } else {
                    CommonLogger.error("Send mail forget password fail for: " + email);
                }
            } else {
                CommonLogger.error("Send mail create user fail format: " + message);
            }
        } catch (Exception e) {
            CommonLogger.error("Message not json format: " + message);
        }
    }

}
