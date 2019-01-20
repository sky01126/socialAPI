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

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * 로그인 확인 페이지 필터
 *
 * 로그인 아이디 / 비밀번호를 DB에서 조회해서 로그인 성공 실패를 결정한다.
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @see AbstractAuthenticationProcessingFilter
 * @since 7.0
 */
public class GenerateTokenForUserFilter extends AbstractAuthenticationProcessingFilter {

	private static final Logger log = LoggerFactory.getLogger(GenerateTokenForUserFilter.class);

	private boolean continueChainBeforeSuccessfulAuthentication = false;

	private SessionAuthenticationStrategy sessionStrategy = new NullAuthenticatedSessionStrategy();

	public GenerateTokenForUserFilter(String pattern, AuthenticationManager manager,
			AuthenticationSuccessHandler success, AuthenticationFailureHandler failure) {
		// 로그인 아이디 비밀번호 체크 API 설정.
		super(new AntPathRequestMatcher(pattern, "POST"));

		setAuthenticationManager(manager);
		setAuthenticationSuccessHandler(success);
		setAuthenticationFailureHandler(failure);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		if (!requiresAuthentication(httpRequest, httpResponse)) {
			log.debug("Not request is to process authentication");
			chain.doFilter(httpRequest, httpResponse);
			return;
		}
		if (log.isDebugEnabled()) {
			log.debug("Request is to process authentication");
		}

		Authentication authResult;
		try {
			authResult = attemptAuthentication(httpRequest, httpResponse);
			if (authResult == null) {
				// return immediately as subclass has indicated that it hasn't completed
				// authentication
				return;
			}
			sessionStrategy.onAuthentication(authResult, httpRequest, httpResponse);
		} catch (InternalAuthenticationServiceException failed) {
			log.error("An internal error occurred while trying to authenticate the user.", failed);
			unsuccessfulAuthentication(httpRequest, httpResponse, failed);
			return;
		} catch (AuthenticationException failed) {
			// Authentication failed
			unsuccessfulAuthentication(httpRequest, httpResponse, failed);
			return;
		} catch (Exception e) {
			error(httpResponse, e);
			return;
		}
		// Authentication success
		if (continueChainBeforeSuccessfulAuthentication) {
			chain.doFilter(httpRequest, httpResponse);
		}
		successfulAuthentication(httpRequest, httpResponse, chain, authResult);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		try {
			String jsonString = IOUtils.toString(request.getInputStream(), "UTF-8");

			/* using org.json */
			JSONObject userJson = new JSONObject(jsonString);
			String userid = userJson.getString("userid");
			String password = userJson.getString("passwd");
			if (log.isDebugEnabled()) {
				log.info("Usern ID: {}, Password: {}", userid, password);
			} else if (log.isInfoEnabled()) {
				log.info("User ID: {}", userid);
			}
			Authentication authResult = new UsernamePasswordAuthenticationToken(userid, password);
			log.debug("{}:{}", authResult.getPrincipal(), authResult.getCredentials());

			// This will take to successfulAuthentication or faliureAuthentication function
			/** {@link SecurityAuthenticationManager} */
			return getAuthenticationManager().authenticate(authResult);
		} catch (JSONException | AuthenticationException e) {
			log.error("Login Failure", e);
			throw new AuthenticationServiceException(e.getMessage(), e);
		}
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		log.warn("Login Fail...");

		/** {@link GenerateAuthenticationFailureHandler} */
		super.unsuccessfulAuthentication(request, response, failed);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		log.debug("Login Success...");

		/** {@link GenerateAuthenticationSuccessHandler} */
		try {
			super.successfulAuthentication(request, response, chain, authResult);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			error(response, e);
		}
	}

	@Override
	public void setContinueChainBeforeSuccessfulAuthentication(boolean continueChainBeforeSuccessfulAuthentication) {
		super.setContinueChainBeforeSuccessfulAuthentication(continueChainBeforeSuccessfulAuthentication);
		this.continueChainBeforeSuccessfulAuthentication = continueChainBeforeSuccessfulAuthentication;
	}

	@Override
	public void setSessionAuthenticationStrategy(SessionAuthenticationStrategy sessionStrategy) {
		super.setSessionAuthenticationStrategy(sessionStrategy);
		this.sessionStrategy = sessionStrategy;
	}

	/**
	 * 인증 처리 중 발생하는 이외의 에러가 발생하면 처리한다.
	 *
	 * @param response HttpServletResponse
	 * @param failed Exception
	 * @throws IOException
	 */
	private void error(HttpServletResponse response, Exception failed) throws IOException {
		log.error(failed.getMessage(), failed);
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("result_code", HttpStatus.INTERNAL_SERVER_ERROR.value());
		map.put("result_msg", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.getWriter().write(ow.writeValueAsString(map));
		response.getWriter().flush();
	}

}
