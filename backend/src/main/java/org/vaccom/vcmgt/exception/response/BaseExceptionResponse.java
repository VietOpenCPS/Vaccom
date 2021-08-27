package org.vaccom.vcmgt.exception.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author TrinhNX
 * @since 8/27/2021
 */
@Setter
@Getter
@AllArgsConstructor
public class BaseExceptionResponse {
    private String message;
}
