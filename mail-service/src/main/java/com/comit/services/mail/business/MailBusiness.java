package com.comit.services.mail.business;

public interface MailBusiness {
    boolean sendQrCodeMail(String email, String fullname, String employeeCode, String organizationName, String locationName, String locationCode, String type);

    boolean sendForgetPasswordMail(Integer id, String fullname, String email, String code);

    boolean sendConfirmCreateUserMail(Integer id, String fullname, String email, String code);
}
