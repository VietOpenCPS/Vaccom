package org.vaccom.vcmgt.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.web.filter.OncePerRequestFilter;
import org.vaccom.vcmgt.entity.KhoaDangKy;
import org.vaccom.vcmgt.entity.KhoaTruyCap;
import org.vaccom.vcmgt.entity.NguoiDung;
import org.vaccom.vcmgt.entity.VaiTro;
import org.vaccom.vcmgt.security.JwtTokenProvider;
import org.vaccom.vcmgt.service.KhoaDangKyService;
import org.vaccom.vcmgt.service.KhoaTruyCapService;
import org.vaccom.vcmgt.service.NguoiDungService;
import org.vaccom.vcmgt.service.impl.CustomUserDetailsServiceImpl;
import org.vaccom.vcmgt.util.RequestUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final Log _log = LogFactory.getLog(JwtAuthenticationFilter.class);

	@Autowired
	private CustomUserDetailsServiceImpl customUserDetailsService;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private NguoiDungService nguoiDungService;

	@Autowired
	private KhoaTruyCapService khoaTruyCapService;

	@Autowired
	private KhoaDangKyService khoaDangKyService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = RequestUtil.getTokenFromRequest(request);

		// System.out.println("token: " + token);

		String requestUri = request.getRequestURI();

		String method = request.getMethod();

		// && !requestUri.contains("/rest/v1/app/add/nguoitiemchung")

		if (method.equalsIgnoreCase("post") || method.equalsIgnoreCase("put") || method.equalsIgnoreCase("patch")
				|| method.equalsIgnoreCase("delete") || method.equalsIgnoreCase("get"))

			try {
				if (!requestUri.contains("/rest/v1/security/login") && !requestUri.contains("/rest/v1/security/logout")
						&& !requestUri.contains("/rest/v1/app/update/phieuhentiem/checkin")
						&& !requestUri.contains("/rest/v1/app/get/giaydiduong-maqr")) {

					KhoaTruyCap khoaTruyCap = khoaTruyCapService.findByKhoaTruyCap(token);

					if (khoaTruyCap != null) {
						String doiTuong = khoaTruyCap.getDoiTuong();
						NguoiDung nguoiDung = nguoiDungService.findByTenDanNhap(doiTuong);

						if (nguoiDung == null) {
							return;
						}

						KhoaDangKy khoaDangKy = khoaDangKyService.findByNguoiDungID(nguoiDung.getId());

						if (khoaDangKy == null) {
							return;
						}

						VaiTro vaiTro = tokenProvider.getVaiTroFromToken(token, khoaDangKy.getKhoaBiMat());

						request.setAttribute("_VAI_TRO", vaiTro);

						request.setAttribute("_TEN_DANG_NHAP", nguoiDung.getTenDangNhap());

						request.setAttribute("_ID", nguoiDung.getId());

						if (tokenProvider.validateToken(token, khoaDangKy.getKhoaBiMat())) {
							UserDetails userDetails = customUserDetailsService
									.loadUserByUsername(nguoiDung.getTenDangNhap());

							UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
									userDetails, null, userDetails.getAuthorities());

							authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

							SecurityContextHolder.getContext().setAuthentication(authentication);
						}
					}

				}

			} catch (Exception ex) {
				_log.error("Could not set user authentication in security context", ex);
			}

		filterChain.doFilter(request, response);
	}

}
