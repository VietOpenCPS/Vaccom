package org.vaccom.vcmgt.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.vaccom.vcmgt.constant.AppConstant.AuthorizationType;

/**
 * @author TrinhNX
 * @since 8/27/2021
 */
@Setter
@Getter
@Builder
public class UserLoginResponse {
    private long userId;
    private String accessToken;
    private long expiresIn;
    private String roleName;
    @Builder.Default
    private AuthorizationType tokenType = AuthorizationType.BEARER;
}
