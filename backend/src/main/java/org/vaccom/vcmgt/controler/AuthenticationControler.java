package org.vaccom.vcmgt.controler;

import java.util.Base64;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.vaccom.vcmgt.entity.KhoaDangKy;
import org.vaccom.vcmgt.entity.KhoaTruyCap;
import org.vaccom.vcmgt.entity.NguoiDung;
import org.vaccom.vcmgt.security.JwtTokenProvider;
import org.vaccom.vcmgt.service.KhoaDangKyService;
import org.vaccom.vcmgt.service.KhoaTruyCapService;
import org.vaccom.vcmgt.service.NguoiDungService;
import org.vaccom.vcmgt.util.MessageUtil;
import org.vaccom.vcmgt.util.RoleUtil;
import org.vaccom.vcmgt.util.VaccomUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.liferay.petra.string.StringPool;

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

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> doLogin(HttpServletRequest request, HttpServletResponse response,
			@RequestHeader(value = "User-Agent") String userAgent) {

		ObjectMapper mapper = new ObjectMapper();

		ObjectNode result = mapper.createObjectNode();

		// HttpSession session = request.getSession();

		// session.setMaxInactiveInterval(expireIn);

		// String sessionId = session.getId().toLowerCase();

		String authorization = request.getHeader("Authorization");

		String tenDangNhap = StringPool.BLANK;

		String matKhau = StringPool.BLANK;

		String tenVaiTro = StringPool.BLANK;

		String token = StringPool.BLANK;

		if (authorization != null) {

			try {
				authorization = authorization.replace("Basic ", StringPool.BLANK);
				authorization = new String(Base64.getDecoder().decode(authorization.getBytes()));
				String[] basicInfo = authorization.split(StringPool.COLON);
				if (basicInfo != null && basicInfo.length == 2) {
					tenDangNhap = basicInfo[0];
					matKhau = basicInfo[1];
				}
			} catch (Exception e) {
				if (e instanceof IllegalArgumentException) {
					_log.info("Illegal base64 character 5f");
					try {
						authorization = new String(Base64.getUrlDecoder().decode(authorization.getBytes()));

					} catch (Exception e1) {
						if (e1 instanceof IllegalArgumentException) {
							_log.info("Illegal base64 character 2f");
						} else {
							_log.error(e1);
						}
					}
				}
			}
		}

		try {

			NguoiDung nguoiDung = nguoiDungService.findByTenDanNhap(tenDangNhap);
			
			KhoaDangKy khoaDangKy = khoaDangKyService.findByNguoiDungID(nguoiDung.getId());

			if (nguoiDung != null && !nguoiDung.isKhoaTaiKhoan()) {

				Authentication authentication = authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(tenDangNhap, matKhau));

				SecurityContextHolder.getContext().setAuthentication(authentication);

				if (authentication.isAuthenticated()) {

					token = tokenProvider.generateToken(tenDangNhap, expireIn);

					KhoaTruyCap khoaTruyCap = khoaTruyCapService.findByDoiTuong(nguoiDung.getTenDangNhap());

					if (khoaTruyCap == null) {
						khoaTruyCap = new KhoaTruyCap();
					}

					khoaTruyCap.setDoiTuong(tenDangNhap);

					khoaTruyCap.setKhoaTruyCap(token);

					khoaTruyCapService.updateKhoaTruyCap(khoaTruyCap);

				}

				tenVaiTro = RoleUtil.getTenVaiTro(nguoiDung);

			} else {
				String msg = MessageUtil.getVNMessageText("nguoidung.not_exist_or_locked");

				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(msg);
			}

			result.put("access_token", token);

			result.put("expires_in", expireIn);

			result.put("role_name", tenVaiTro);

			result.put("expires_in", expireIn);

			result.put("token_type", "Bearer");
			
			result.put("user_id", nguoiDung.getId());

			return ResponseEntity.status(HttpStatus.OK).body(result.toString());

		} catch (Exception e) {

			e.printStackTrace();

			_log.error(e);

			String msg = MessageUtil.getVNMessageText("login.failed");

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(msg);
		}

	}

}
