package org.vaccom.vcmgt.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * @author TrinhNX
 * @since 8/27/2021
 */
@Setter
@Getter
public class UserLoginRequest {
    @NotEmpty
    private String userName;
    @NotEmpty
    private String password;
}
