package com.comit.services.mail.business;

import com.comit.services.mail.constant.Const;
import com.comit.services.mail.loging.model.CommonLogger;
import com.comit.services.mail.model.entity.Mail;
import com.comit.services.mail.service.MailServices;
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
    public boolean sendQrCodeMail(String email, String fullname, String employeeCode, String organizationName, String locationName, String locationCode, String type) {
        Mail mail = new Mail();
        mail.setMailFrom(env.getProperty("spring.mail.username"));
        mail.setMailTo(email);
        mail.setMailSubject(Objects.equals(type, Const.TIME_KEEPING_MODULE) ? "Nhận thông báo chấm công qua Telegram" : "Nhận thông báo cảnh báo đột nhập qua Telegram");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", fullname);
        model.put("location", locationName);
        model.put("organization", organizationName);
        model.put("qrcodeImage", env.getProperty("qrcode.image.url"));
        model.put("qrcodeUrl", env.getProperty("qrcode.url"));
        model.put("code", employeeCode + "_" + locationCode);
        mail.setProps(model);

        try {
            mailServices.sendEmail(mail, "send-qrcode-template");
        } catch (Exception e) {
            CommonLogger.error("Error send mail: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean sendForgetPasswordMail(Integer id, String fullname, String email, String code) {
        Mail mail = new Mail();
        mail.setMailFrom(env.getProperty("spring.mail.username"));
        mail.setMailTo(email);
        mail.setMailSubject("Change password");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", fullname);
        model.put("location", Const.MAIL_LOCATION);
        model.put("sign", Const.MAIL_ORGANIZATION_NAME);
        model.put("link", env.getProperty("frontend.server") + "?user_id=" + id + "&code=" + code);
        mail.setProps(model);

        mailServices.sendEmail(mail, "forgetPassword-template");
        return true;
    }

    @Override
    public boolean sendConfirmCreateUserMail(Integer id, String fullname, String username, String email, String code, Boolean isResend) {
        Mail mail = new Mail();
        mail.setMailFrom(env.getProperty("spring.mail.username"));
        mail.setMailTo(email);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", fullname);
        model.put("username", username);
        model.put("location", Const.MAIL_LOCATION);
        model.put("sign", Const.MAIL_ORGANIZATION_NAME);
        model.put("link", env.getProperty("frontend.server") + "?user_id=" + id + "&code=" + code);
        mail.setProps(model);

        try {
            if (isResend) {
                mail.setMailSubject("Confirm recreate user");
                mailServices.sendEmail(mail, "reCreateUser-template");
            } else {
                mail.setMailSubject("Confirm create user");
                mailServices.sendEmail(mail, "createUser-template");
            }
            return true;
        } catch (Exception e) {
            CommonLogger.error(e);
            return false;
        }
    }

    @Override
    public boolean sendAreaRestrictionMail(String email, String employeeName, String areaRestrictionName, String date) {
        Mail mail = new Mail();
        mail.setMailFrom(env.getProperty("spring.mail.username"));
        mail.setMailTo(email);
        mail.setMailSubject("Cảnh báo đột nhập khu vực hạn chế");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", employeeName);
        model.put("areaRestrictionName", areaRestrictionName);
        model.put("date", date);
        mail.setProps(model);

        try {
            mailServices.sendEmail(mail, "areaRestrictionNotification-template");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean sendTimeKeepingMail(String email, String employeeName, String shiftName, String date, String locationName, String organizationName, String content) {
        Mail mail = new Mail();
        mail.setMailFrom(env.getProperty("spring.mail.username"));
        mail.setMailTo(email);
        mail.setMailSubject("Cảnh báo đi muộn");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", employeeName);
        model.put("shift", shiftName);
        model.put("date", date);
        model.put("location", locationName);
        model.put("organization", organizationName);
        model.put("content", content);
        mail.setProps(model);

        try {
            mailServices.sendEmail(mail, "timeKeepingNotification-template");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
