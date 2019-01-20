/*
 * Copyright ⓒ [2017] KTH corp.All rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */

package com.ha.social.web.util;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.security.core.Authentication;

import com.google.common.collect.Maps;
import com.ha.social.config.Config;
import com.ha.social.security.model.User;
import com.ha.social.security.model.UserAuthentication;
import com.ha.social.security.model.UserToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Json Web Tokeun 로그인 인증 정보 생성 / 암,복호화 유틸리티
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @since 7.0
 */
public final class JWTUtils {

	private JWTUtils() {
		// ignore...
	}

	public static final Authentication verifyToken(final String secretKey, final HttpServletRequest request) {
		return verifyToken(secretKey, request.getHeader(Config.AUTH_TOKEN));
	}

	public static final Authentication verifyToken(final String secretKey, final String token) {
		if (!StringUtils.isBlank(token)) {
			UserToken userToken = parseUserFromToken(secretKey, token.replace("Bearer", "").trim());
			return new UserAuthentication(userToken);
		}
		return null;
	}

	public static final String createTokenForUser(final String secretKey, final UserToken token) {
		return createTokenForUser(secretKey, token.getUser());
	}

	@SuppressWarnings("all")
	public static final String createTokenForUser(final String secretKey, final User user) {
		DateTime expireDate = DateTime.now().plusSeconds(Config.TOKEN_EXPIRE);
		Map<String, Object> claims = Maps.newHashMap();
		claims.put(Config.USERID, user.getUserId());
		claims.put(Config.USERNAME, user.getUserName());
		claims.put(Config.ROLE, user.getAuthorities());
		String jwtToken = Jwts.builder() // JwtsBuilder.

				// Setting Expire Date.
				.setExpiration(expireDate.toDate())

				// Setting Subject
				.setSubject(user.getUserName())

				// The value is the timestamp when the JWT was created.
				.setIssuedAt(DateTime.now().toDate())

				// Sets the JWT payload to be a JSON Claims instance populated by the specified name/value pairs
				.setClaims(claims)

				// Compression Codecs
				.compressWith(CompressionCodecs.GZIP)

				// Signs the constructed JWT using the specified algorithm with the specified key, producing a JWS.
				.signWith(SignatureAlgorithm.HS256, secretKey)

				// URL-safe JWT string.
				.compact();
		return jwtToken;
	}

	/**
	 * Token을 파싱한다.
	 *
	 * @param authToken Auth-Token
	 * @return Token
	 */
	@SuppressWarnings("unchecked")
	private static final UserToken parseUserFromToken(final String secretKey, final String authToken) {
		Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken).getBody();
		User user = new User();
		user.setUserId((String) claims.get(Config.USERID));
		user.setUserName((String) claims.get(Config.USERNAME));
		user.setAuthorities((List<String>) claims.get(Config.ROLE));
		return new UserToken(user, new DateTime(claims.getExpiration()));
	}

}
