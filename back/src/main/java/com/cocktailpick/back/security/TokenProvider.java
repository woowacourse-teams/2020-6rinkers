package com.cocktailpick.back.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.cocktailpick.back.config.AppProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class TokenProvider {

	private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

	private AppProperties appProperties;

	public TokenProvider(AppProperties appProperties) {
		this.appProperties = appProperties;
	}

	public String createToken(Authentication authentication) {
		UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());

		return Jwts.builder()
			.setSubject(Long.toString(userPrincipal.getId()))
			.setIssuedAt(new Date())
			.setExpiration(expiryDate)
			.signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
			.compact();
	}

	public Long getUserIdFromToken(String token) {
		Claims claims = Jwts.parser()
			.setSigningKey(appProperties.getAuth().getTokenSecret())
			.parseClaimsJws(token)
			.getBody();

		return Long.parseLong(claims.getSubject());
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			logger.error("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			logger.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			logger.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty.");
		}
		return false;
	}

}
