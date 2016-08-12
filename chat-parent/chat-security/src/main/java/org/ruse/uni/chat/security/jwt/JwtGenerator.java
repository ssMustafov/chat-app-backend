package org.ruse.uni.chat.security.jwt;

import java.security.Key;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.ruse.uni.chat.core.configuration.ConfigurationProperty;
import org.ruse.uni.chat.security.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@ApplicationScoped
public class JwtGenerator {

	@Inject
	@ConfigurationProperty(name = "security.jwt.key")
	private String jwtSigningKeyConfiguration;

	private Key jwtKey;

	@PostConstruct
	private void init() {
		jwtKey = new SecretKeySpec(Base64.getEncoder().encode(jwtSigningKeyConfiguration.getBytes()), "HmacSHA256");
	}

	public String generate(User user) {
		return Jwts.builder().setSubject(user.getUsername()).setIssuer(user.getEmail())
				.setIssuedAt(user.getRegisteredOn()).signWith(SignatureAlgorithm.HS512, jwtKey).compact();
	}

	public User parse(String jwt) {
		Jws<Claims> parsedClaimsJws = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(jwt);
		Claims claims = parsedClaimsJws.getBody();

		org.ruse.uni.chat.core.entity.User user = new org.ruse.uni.chat.core.entity.User();
		user.setEmail(claims.getIssuer());
		user.setUsername(claims.getSubject());
		user.setRegisteredOn(claims.getIssuedAt());
		return new User(user);
	}

}
