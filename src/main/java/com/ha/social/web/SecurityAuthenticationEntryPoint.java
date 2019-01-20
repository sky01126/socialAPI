/*
 * Copyright ⓒ [2017] KTH corp.All rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */

package com.ha.social.web;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ha.social.web.util.RequestUtils;

/**
 * 인증 토큰 없이 요청 에러처리 - HTTP Status : 401 (Unauthorized)
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @see AuthenticationEntryPoint
 * @since 7.0
 */
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static final Logger log = LoggerFactory.getLogger(SecurityAuthenticationEntryPoint.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		log.debug("Security Authentication Entry Point...");
		if (RequestUtils.isResource(request.getRequestURI())) {
			return;
		}

		Map<String, Object> map = new LinkedHashMap<>();
		map.put("result_code", HttpStatus.UNAUTHORIZED.value());
		map.put("result_msg", HttpStatus.UNAUTHORIZED.getReasonPhrase());

		ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.getWriter().write(writer.writeValueAsString(map));
		response.getWriter().flush();
	}

}
