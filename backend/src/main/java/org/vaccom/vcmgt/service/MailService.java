package org.vaccom.vcmgt.service;

import org.vaccom.vcmgt.model.mail.SimpleMail;

/**
 * @author TrinhNX
 * @since 8/29/2021
 */
public interface MailService {
    /**
     * Perform sending to {@code mail} <br>
     * Exception is logged and not rethrown
     *
     * @param mail
     */
    void sendMail(SimpleMail mail);
}
