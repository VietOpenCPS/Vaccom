package org.vaccom.vcmgt.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.vaccom.vcmgt.config.EmailConfig;
import org.vaccom.vcmgt.constant.AppConstant;
import org.vaccom.vcmgt.entity.NguoiTiemChung;
import org.vaccom.vcmgt.listener.event.NewAccountCreatedEvent;
import org.vaccom.vcmgt.model.mail.SimpleMail;
import org.vaccom.vcmgt.service.MailService;
import org.vaccom.vcmgt.service.NguoiTiemChungService;
import org.vaccom.vcmgt.util.VaccomUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Handle event which is published inside system here
 *
 * @author TrinhNX
 * @since 8/29/2021
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class VaccomEventListener {
    private final EmailConfig mailConfig;
    private final MailService mailService;
    private final NguoiTiemChungService tiemChungService;

    @Async
    @EventListener(NewAccountCreatedEvent.class)
    public void handleNewAccountCreated(NewAccountCreatedEvent event) {
        NguoiTiemChung nguoiTiemChung = tiemChungService.findById(event.getUserId());
        if (nguoiTiemChung == null || StringUtils.isEmpty(nguoiTiemChung.getMaQR())) {
            log.warn("Can not lookup user {}", event.getUserId());
            return;
        }
        final EmailConfig.EmailMeta meta = mailConfig.getCreateAccount();
        final Map<String, String> mailBody = new HashMap<>();
        VaccomUtil.generateEncodedQRImage(nguoiTiemChung.getMaQR()).ifPresent(base64 -> mailBody.put("base64Img", base64));
        mailBody.put("domain", AppConstant.APP_DOMAIN);
        mailBody.put("fullName", nguoiTiemChung.getHoVaTen());
        mailBody.put("maQR", nguoiTiemChung.getMaQR());
        mailService.sendMail(SimpleMail.builder()
                .from(mailConfig.getSupport())
                .to(nguoiTiemChung.getEmail())
                .subject(meta.getSubject())
                .body(StringSubstitutor.replace(meta.getBody(), mailBody))
                .build());
    }
}
