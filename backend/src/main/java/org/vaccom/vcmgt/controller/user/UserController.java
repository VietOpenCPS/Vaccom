package org.vaccom.vcmgt.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vaccom.vcmgt.dto.response.CheckinQRResponse;
import org.vaccom.vcmgt.entity.LichTiemChung;
import org.vaccom.vcmgt.exception.NotFoundException;
import org.vaccom.vcmgt.service.LichTiemChungService;
import org.vaccom.vcmgt.service.PhieuHenTiemService;

/**
 * TODO: Currently not checking authority of this
 *
 * @author TrinhNX
 * @since 8/27/2021
 */
@Slf4j
@RestController
@RequestMapping("/rest/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final LichTiemChungService lichTiemChungService;
    private final PhieuHenTiemService

    @PutMapping("/checkin/{qrCode}")
    public CheckinQRResponse checkinQR(@PathVariable String qrCode) {
        // B1. Lay thong tin lich tiem chung dua vao qrCode
        // B2. Update thong tin nguoi dung vao phieu hen tiem
        final LichTiemChung tiemChung = lichTiemChungService.findByQRCode(qrCode).orElseThrow(NotFoundException::new);
        if (!tiemChung.isValidPlan()) {
            log.error("Invalid plan: {}", tiemChung);
            throw new NotFoundException();
        }

        return CheckinQRResponse.builder().build();
    }
}
