/*
 * Copyright ⓒ [2017] KTH corp.All rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */
package com.ha.social.web.filter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.context.NullSecurityContextRepository;
import org.springframework.security.web.session.SessionManagementFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ha.social.config.Config;
import com.ha.social.service.SecurityAuthorizationService;

/**
 * 사용자의 인증 정보 조회 및 권한 설정 필터
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @see SessionManagementFilter
 * @since 7.0
 */
public class SecuritySessionManagementFilter extends SessionManagementFilter {

	private static final Logger log = LoggerFactory.getLogger(SecuritySessionManagementFilter.class);

	private final SecurityAuthorizationService service;

	public SecuritySessionManagementFilter(SecurityAuthorizationService service) {
		super(new NullSecurityContextRepository());
		this.service = service;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		if (log.isDebugEnabled()) {
			log.debug("인증된 사용자와 관련된 모든 세션을 추적하는 필터 [{}]", httpRequest.getRequestURL());
		}
		try {
			// TODO Token 정보를 가져온다.
			String authToken = httpRequest.getHeader(Config.AUTH_TOKEN);
			if (!StringUtils.isEmpty(authToken)) {
				// TODO 인정 토큰 확인 및 권한 설정
				log.debug("Auth Token : {}", authToken);
				service.setAuthentication(authToken);
			}
			super.doFilter(request, response, chain);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			error(httpResponse, e);
		}
	}

	/**
	 * 에러 처리 메소드.
	 *
	 * @param response HttpServletResponse
	 * @param error the exception
	 * @throws IOException the io exception
	 */
	private void error(HttpServletResponse response, Exception error) throws IOException {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("result_code", HttpStatus.INTERNAL_SERVER_ERROR.value());
		map.put("result_msg", error.toString());

		ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
		response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.getWriter().write(writer.writeValueAsString(map));
		response.getWriter().flush();
	}

}
