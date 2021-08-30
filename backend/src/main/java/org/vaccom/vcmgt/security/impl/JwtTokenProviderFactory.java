package org.vaccom.vcmgt.security.impl;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.KhoaDangKy;
import org.vaccom.vcmgt.entity.NguoiDung;
import org.vaccom.vcmgt.entity.VaiTro;
import org.vaccom.vcmgt.security.JwtTokenProvider;
import org.vaccom.vcmgt.service.KhoaDangKyService;
import org.vaccom.vcmgt.service.NguoiDungService;
import org.vaccom.vcmgt.util.VaccomUtil;

import com.liferay.petra.string.StringPool;

@Service
public class JwtTokenProviderFactory implements JwtTokenProvider {

	private final Log _log = LogFactory.getLog(JwtTokenProvider.class);

	@Autowired
	NguoiDungService nguoiDungService;

	@Autowired
	KhoaDangKyService khoaDangKyService;

	@Override
	public String generateToken(Authentication authentication, int exprireIn) {

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		return generateToken(userDetails, exprireIn);
	}

	@Override
	public String generateToken(Map<String, Object> claims, String subject, String secret, int exprireIn) {

		Date now = new Date();

		Date expiryDate = new Date(now.getTime() + exprireIn);

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	@Override
	public String generateToken(UserDetails userDetails, int exprireIn) {

		NguoiDung nguoiDung = nguoiDungService.findByTenDanNhap(userDetails.getUsername());

		if (nguoiDung == null) {
			return "error01";
		}

		KhoaDangKy khoa = khoaDangKyService.findByNguoiDungID(nguoiDung.getId());

		if (khoa == null) {
			return "error02";
		}

		Date now = new Date();

		Date expiryDate = new Date(now.getTime() + exprireIn);

		return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date()).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, khoa.getKhoaBiMat()).compact();
	}

	@Override
	public String generateToken(String subject, int exprireIn) {

		NguoiDung nguoiDung = nguoiDungService.findByTenDanNhap(subject);

		if (nguoiDung == null) {
			return "error01";
		}

		KhoaDangKy khoa = khoaDangKyService.findByNguoiDungID(nguoiDung.getId());

		if (khoa == null) {
			return "error02";
		}

		Date now = new Date();

		Date expiryDate = new Date(now.getTime() + exprireIn * 1000);

		return Jwts.builder().setSubject(subject).setIssuedAt(new Date()).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, khoa.getKhoaBiMat()).compact();
	}

	@Override
	public Claims getAllClaimsFromToken(String token, String secret) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	@Override
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver, String secret) {
		final Claims claims = getAllClaimsFromToken(token, secret);
		return claimsResolver.apply(claims);
	}

	@Override
	public Date getExpirationDateFromToken(String token, String secret) {
		return getClaimFromToken(token, Claims::getExpiration, secret);
	}

	@Override
	public String getSubjectFromToken(String token, String secret) {
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

		return claims.getSubject();
	}

	@Override
	public String getUsernameFromToken(String token, String secret) {
		return getClaimFromToken(token, Claims::getSubject, secret);
	}
	
	@Override
	public VaiTro getVaiTroFromToken(String token, String secret) {
		
		String tenDangNhap = getUsernameFromToken(token, secret);

		NguoiDung nguoiDung = nguoiDungService.findByTenDanNhap(tenDangNhap);
		
		return new VaiTro(nguoiDung);
	}

	@Override
	public Boolean isTokenExpired(String token, String secret) {
		final Date expiration = getExpirationDateFromToken(token, secret);
		return expiration.before(new Date());
	}

	@Override
	public boolean validateToken(String token, String secret) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (SignatureException ex) {
			_log.error("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			_log.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			_log.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			_log.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			_log.error("JWT claims string is empty.");
		}
		return false;
	}

	@Override
	public Boolean validateToken(String token, UserDetails userDetails) {

		NguoiDung nguoiDung = nguoiDungService.findByTenDanNhap(userDetails.getUsername());

		if (nguoiDung == null) {
			return false;
		}

		KhoaDangKy khoa = khoaDangKyService.findByNguoiDungID(nguoiDung.getId());

		if (khoa == null) {
			return false;
		}

		return validateToken(token, khoa.getKhoaBiMat());
	}

}
