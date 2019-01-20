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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * 인증 실패 Handler.
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @see SimpleUrlAuthenticationFailureHandler
 * @since 7.0
 */
public class GenerateAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private static final Logger log = LoggerFactory.getLogger(GenerateAuthenticationSuccessHandler.class);

	public GenerateAuthenticationFailureHandler() {
		// ignore..
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// TODO 로그인 실패에 대한 정보 설정.
		log.debug("로그인 실패에 대한 정보 설정.");
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("result_code", HttpStatus.UNAUTHORIZED.value());
		map.put("result_msg", HttpStatus.UNAUTHORIZED.getReasonPhrase());

		ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
		response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.getWriter().write(writer.writeValueAsString(map));
		response.getWriter().flush();
	}

}
