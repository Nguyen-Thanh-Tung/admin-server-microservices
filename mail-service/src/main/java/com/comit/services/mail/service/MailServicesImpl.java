package com.comit.services.mail.service;

import com.comit.services.mail.constant.Const;
import com.comit.services.mail.model.entity.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class MailServicesImpl implements MailServices {
    @Autowired
    JavaMailSender mailSender;

    @Autowired
    SpringTemplateEngine templateEngine;

    @Async
    public void sendEmail(Mail mail, String templateName) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Context context = new Context();
            context.setVariables(mail.getProps());
            String html = templateEngine.process(templateName, context);
            mimeMessageHelper.setSubject(mail.getMailSubject());
            mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom(), Const.MAIL_ORGANIZATION_NAME));
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setText(html, true);

            String[] attachments = mail.getAttachments();

            if (attachments != null && attachments.length > 0) {
                for (String attachment : attachments) {
                    FileSystemResource file = new FileSystemResource(attachment);
                    mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
                }
            }

            mailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
