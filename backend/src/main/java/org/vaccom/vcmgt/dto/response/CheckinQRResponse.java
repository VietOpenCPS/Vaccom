package org.vaccom.vcmgt.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author TrinhNX
 * @since 8/27/2021
 */
@Setter
@Getter
@Builder
public class CheckinQRResponse {
    private String facilityName;
    private String facilityAddress;
    private String facilityTel;
}
