package org.vaccom.vcmgt.security;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.VaiTro;

@Service
public interface JwtTokenProvider {

	public String generateToken(Authentication authentication, int exprireIn);

	// while creating the token -
	// 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
	// 2. Sign the JWT using the HS512 algorithm and secret key.
	// 3. According to JWS Compact
	// Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	// compaction of the JWT to a URL-safe string
	public String generateToken(Map<String, Object> claims, String subject, String secret, int exprireIn);

	// generate token for user
	public String generateToken(UserDetails userDetails, int exprireIn);
	
	public String generateToken(String subject, int exprireIn);

	// for retrieveing any information from token we will need the secret key
	public Claims getAllClaimsFromToken(String token, String secret);

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver, String secret);

	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token, String secret);

	public String getSubjectFromToken(String token, String secret);

	// retrieve username from jwt token
	public String getUsernameFromToken(String token, String secret);
	
	// retrieve username from jwt token
	public VaiTro getVaiTroFromToken(String token, String secret);
	
	// check if the token has expired
	public Boolean isTokenExpired(String token, String secret);

	public boolean validateToken(String token, String secret);

	// validate token
	public Boolean validateToken(String token, UserDetails userDetails);
}
