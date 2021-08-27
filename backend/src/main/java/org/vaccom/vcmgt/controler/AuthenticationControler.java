package org.vaccom.vcmgt.controler;

import com.liferay.petra.string.StringPool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.vaccom.vcmgt.dto.request.UserLoginRequest;
import org.vaccom.vcmgt.dto.response.UserLoginResponse;
import org.vaccom.vcmgt.entity.KhoaDangKy;
import org.vaccom.vcmgt.entity.KhoaTruyCap;
import org.vaccom.vcmgt.entity.NguoiDung;
import org.vaccom.vcmgt.exception.AuthException;
import org.vaccom.vcmgt.security.JwtTokenProvider;
import org.vaccom.vcmgt.service.KhoaDangKyService;
import org.vaccom.vcmgt.service.KhoaTruyCapService;
import org.vaccom.vcmgt.service.NguoiDungService;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/v1/security")
public class AuthenticationControler {

    private Log _log = LogFactory.getLog(AuthenticationControler.class);

    @Value("${spring.security.expire-in}")
    private int expireIn;

    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private KhoaTruyCapService khoaTruyCapService;

    @Autowired
    private KhoaDangKyService khoaDangKyService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping(value = "/login")
    public UserLoginResponse doLogin(@RequestBody @Valid UserLoginRequest dto) {
        final String tenDangNhap = dto.getUserName();
        final String matKhau = dto.getPassword();
        NguoiDung nguoiDung = nguoiDungService.findByTenDanNhap(tenDangNhap);
        final boolean isUserValid = nguoiDung != null && !nguoiDung.isKhoaTaiKhoan();
        if (!isUserValid) throw new AuthException("nguoidung.not_exist_or_locked");
        KhoaDangKy khoaDangKy = khoaDangKyService.findByNguoiDungID(nguoiDung.getId());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(tenDangNhap, matKhau));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if (!authentication.isAuthenticated()) {
            throw new AuthException("login.failed");
        }
        final String token = tokenProvider.generateToken(tenDangNhap, expireIn);
        KhoaTruyCap khoaTruyCap = khoaTruyCapService.findByDoiTuong(nguoiDung.getTenDangNhap());
        if (khoaTruyCap == null) {
            khoaTruyCap = new KhoaTruyCap();
        }
        khoaTruyCap.setDoiTuong(tenDangNhap);
        khoaTruyCap.setKhoaTruyCap(token);
        khoaTruyCapService.updateKhoaTruyCap(khoaTruyCap);
        return UserLoginResponse.builder()
                .accessToken(token)
                .roleName(khoaDangKy != null ? khoaDangKy.getPhamVi() : StringPool.BLANK)
                .expiresIn(expireIn)
                .userId(nguoiDung.getId())
                .build();
    }
}
