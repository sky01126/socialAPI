/*
 * Copyright ⓒ [2017] KTH corp.All rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */

package com.ha.social.web.handler;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ha.social.config.Config;
import com.ha.social.security.model.User;
import com.ha.social.security.model.UserToken;
import com.ha.social.service.SecurityAuthorizationService;
import com.ha.social.web.util.JWTUtils;

/**
 * 인증 성공 Handler.
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @see SimpleUrlAuthenticationFailureHandler
 * @since 7.0
 */
public class GenerateAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private static final Logger log = LoggerFactory.getLogger(GenerateAuthenticationSuccessHandler.class);

	private final SecurityAuthorizationService service;

	public GenerateAuthenticationSuccessHandler(SecurityAuthorizationService service) {
		this.service = service;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		// TODO 로그인 성공에 대한 정보 설정.
		log.debug("로그인 성공에 대한 정보 설정.");
		// 로그인 성공에 대한 정보 설정
		UserToken userToken = (UserToken) authentication.getPrincipal();
		if (log.isDebugEnabled()) {
			log.debug(userToken.getUser().toString());
		}

		// 암호화키 생성.
		User user = userToken.getUser();
		String jwtToken = JWTUtils.createTokenForUser(Config.SECRET_KEY, userToken);
		log.debug("JWT Token : {}", jwtToken);

		// TODO Token을 DB에 저장 시 사용.
		service.setAuthorizationInfo(jwtToken, user);

		Map<String, Object> map = new LinkedHashMap<>();
		map.put("result_code", HttpStatus.OK.value());
		map.put("result_msg", HttpStatus.OK.getReasonPhrase());
		map.put(Config.AUTH_TOKEN, jwtToken);

		ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
		response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.setStatus(HttpStatus.OK.value());
		response.getWriter().write(writer.writeValueAsString(map));
		response.getWriter().flush();
	}

}
