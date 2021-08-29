package org.vaccom.vcmgt.model.mail;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A simple mail to send from one to another address
 *
 * @author TrinhNX
 * @since 8/29/2021
 */
@Setter
@Getter
@ToString
@Builder
public class SimpleMail {
    private String from;
    private String to;
    private String subject;
    private String body;
}