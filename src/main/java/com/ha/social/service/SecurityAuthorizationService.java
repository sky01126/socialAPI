/*
 * Copyright ⓒ [2017] KTH corp.All rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */

package com.ha.social.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ha.social.config.Config;
import com.ha.social.security.model.User;
import com.ha.social.security.model.UserAuthentication;
import com.ha.social.web.util.JWTUtils;

/**
 * 로그인 인증 서비스.
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @since 7.0
 */
@Service
public class SecurityAuthorizationService {

	private static final Logger log = LoggerFactory.getLogger(SecurityAuthorizationService.class);

	/**
	 * 인증 정보 조회.
	 *
	 * @param secretKey 암/복호화키
	 * @param tokenKey 토큰키
	 * @return Authorization
	 */
	public UserAuthentication getAuthorizationInfo(String secretKey, String tokenKey) {
		return null;
	}

	public void setAuthorizationInfo(String token, User user) {
		// ignore...
	}

	/**
	 * 인정 토큰 확인 및 권한 설정.
	 *
	 * @param token the auth token
	 */
	public void setAuthentication(String token) {
		Authentication authentication = JWTUtils.verifyToken(Config.SECRET_KEY, token);
		log.debug("User authorities: {}", StringUtils.join(authentication.getAuthorities(), ", "));

		// Security Context holder에 인증 정보를 설정한다.
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
