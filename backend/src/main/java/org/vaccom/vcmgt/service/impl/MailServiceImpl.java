package org.vaccom.vcmgt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.constant.AppConstant;
import org.vaccom.vcmgt.model.mail.SimpleMail;
import org.vaccom.vcmgt.service.MailService;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

/**
 * @author TrinhNX
 * @since 8/29/2021
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MailServiceImpl implements MailService {
   // private final JavaMailSender sender;

    @Override
    public void sendMail(SimpleMail mail) {
    	/*
        final MimeMessage message = sender.createMimeMessage();
        final MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());
        try {
            helper.setFrom(new InternetAddress(mail.getFrom(), AppConstant.APP_NAME));
            helper.setTo(mail.getTo());
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getBody(), true);
            sender.send(message);
        } catch (Exception e) {
            log.error("Invalid mail info {}", mail, e);
        }
        */
    }
}