/*
 * Copyright ⓒ [2017] KTH corp.All rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */

package com.ha.social.web.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.ha.social.security.model.UserToken;
import com.ha.social.service.SecurityUserService;

/**
 * 로그인 아이디 비밀번호 유효성 체크
 * 로그인 실패 시 {@link AuthenticationServiceException} 발생.
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @see AuthenticationManager
 * @since 7.0
 */
public class SecurityAuthenticationManager implements AuthenticationManager {

	private static final Logger log = LoggerFactory.getLogger(SecurityAuthenticationManager.class);

	private SecurityUserService userService;

	public SecurityAuthenticationManager(SecurityUserService userService) {
		this.userService = userService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) {
		String username = authentication.getName();
		String password = String.valueOf(authentication.getCredentials());
		log.debug("Username: {}, Password: {}", username, password);

		UserToken token = userService.getUserInfo(username, password);
		return new UsernamePasswordAuthenticationToken(token, password);
	}

}
