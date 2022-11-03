package com.comit.services.mail.business;

public interface MailBusiness {
    boolean sendQrCodeMail(String email, String fullname, String employeeCode, String organizationName, String locationName, String locationCode, String type);

    boolean sendForgetPasswordMail(Integer id, String fullname, String email, String code);

    boolean sendConfirmCreateUserMail(Integer id, String fullname, String username, String email, String code, Boolean isResend);

    boolean sendAreaRestrictionMail(String email, String employeeName, String areaRestrictionName, String date);

    boolean sendTimeKeepingMail(String email, String employeeName, String shiftName, String date, String locationName, String organizationName, String content);
}
