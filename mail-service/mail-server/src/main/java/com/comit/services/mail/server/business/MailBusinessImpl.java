package com.comit.services.mail.server.business;

import com.comit.services.mail.client.request.MailCreateUserRequest;
import com.comit.services.mail.client.request.MailForgetPasswordRequest;
import com.comit.services.mail.client.request.MailQrCodeRequest;
import com.comit.services.mail.server.constant.Const;
import com.comit.services.mail.server.entity.Mail;
import com.comit.services.mail.server.service.MailServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class MailBusinessImpl implements MailBusiness {
    @Autowired
    private MailServices mailServices;
    @Autowired
    private Environment env;

    @Override
    public boolean sendQrCodeMail(MailQrCodeRequest mailQrCodeRequest) {
        Mail mail = new Mail();
        mail.setMailFrom(mailQrCodeRequest.getLocationName());
        mail.setMailTo(mailQrCodeRequest.getMailTo());
        mail.setMailSubject(Objects.equals(mailQrCodeRequest.getType(), Const.TIME_KEEPING_MODULE) ? "Nhận thông báo chấm công qua Telegram" : "Nhận thông báo cảnh báo đột nhập qua Telegram");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", mailQrCodeRequest.getFullname());
        model.put("location", mailQrCodeRequest.getLocationName());
        model.put("organization", mailQrCodeRequest.getOrganizationName());
        model.put("qrcodeImage", env.getProperty("qrcode.image.url"));
        model.put("qrcodeUrl", env.getProperty("qrcode.url"));
        model.put("code", mailQrCodeRequest.getEmployeeCode() + "_" + mailQrCodeRequest.getLocationCode());
        mail.setProps(model);

        mailServices.sendEmail(mail, "send-qrcode-template");
        return true;
    }

    @Override
    public boolean sendForgetPasswordMail(MailForgetPasswordRequest mailForgetPasswordRequest) {
        Mail mail = new Mail();
        mail.setMailFrom(env.getProperty("spring.mail.username"));
        mail.setMailTo(mailForgetPasswordRequest.getMailTo());
        mail.setMailSubject("Change password");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", mailForgetPasswordRequest.getFullname());
        model.put("location", Const.MAIL_LOCATION);
        model.put("sign", Const.MAIL_ORGANIZATION_NAME);
        model.put("link", env.getProperty("frontendServer") + "?user_id=" + mailForgetPasswordRequest.getUserId() + "&code=" + mailForgetPasswordRequest.getCode());
        mail.setProps(model);

        mailServices.sendEmail(mail, "forgetPassword-template");
        return true;
    }

    @Override
    public boolean sendConfirmCreateUserMail(MailCreateUserRequest mailCreateUserRequest) {
        Mail mail = new Mail();
        mail.setMailFrom(env.getProperty("spring.mail.username"));
        mail.setMailTo(mailCreateUserRequest.getEmail());
        mail.setMailSubject("Confirm create user");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", mailCreateUserRequest.getFullName());
        model.put("location", Const.MAIL_LOCATION);
        model.put("sign", Const.MAIL_ORGANIZATION_NAME);
        model.put("link", env.getProperty("frontendServer") + "?user_id=" + mailCreateUserRequest.getId() + "&code=" + mailCreateUserRequest.getCode());
        mail.setProps(model);

        try {
            mailServices.sendEmail(mail, "createUser-template");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
