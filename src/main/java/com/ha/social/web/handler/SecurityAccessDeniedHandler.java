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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * 접근 권한 없이 접근하는 경우 에러 발생 - HTTP Status : 403 (Forbidden)
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @see AccessDeniedHandler
 * @since 7.0
 */
public class SecurityAccessDeniedHandler implements AccessDeniedHandler {

	private static final Logger log = LoggerFactory.getLogger(SecurityAccessDeniedHandler.class);

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex)
			throws IOException, ServletException {
		log.debug("접근 권한이 없습니다.");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			log.warn("User({}) attempted to access the protected URL: {}", auth.getName(), request.getRequestURI());
		}

		Map<String, Object> map = new LinkedHashMap<>();
		map.put("result_code", HttpStatus.FORBIDDEN.value());
		map.put("result_msg", HttpStatus.FORBIDDEN.getReasonPhrase());

		ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
		response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.getWriter().write(writer.writeValueAsString(map));
		response.getWriter().flush();
	}

}
