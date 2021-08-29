package org.vaccom.vcmgt.listener.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;

/**
 * @author TrinhNX
 * @since 8/29/2021
 */
@Getter
@RequiredArgsConstructor
public class NewAccountCreatedEvent {
    /**
     * This user id can be ID, phone number, email or whatever. <br>
     * It is used to notify the user
     */
    private final long userId;
}
