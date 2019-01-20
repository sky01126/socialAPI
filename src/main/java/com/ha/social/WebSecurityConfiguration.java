/*
* Copyright ⓒ [2017] KTH corp.All rights reserved.
*
* This is a proprietary software of KTH corp, and you may not use this file except in
* compliance with license with license agreement with KTH corp. Any redistribution or use of this
* software, with or without modification shall be strictly prohibited without prior written
* approval of KTH corp, and the copyright notice above does not evidence any actual or
* intended publication of such software.
*/

package com.ha.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.ha.social.config.Config;
import com.ha.social.service.SecurityAuthorizationService;
import com.ha.social.service.SecurityUserService;
import com.ha.social.web.SecurityAuthenticationEntryPoint;
import com.ha.social.web.filter.GenerateTokenForUserFilter;
import com.ha.social.web.filter.SecuritySessionManagementFilter;
import com.ha.social.web.handler.GenerateAuthenticationFailureHandler;
import com.ha.social.web.handler.GenerateAuthenticationSuccessHandler;
import com.ha.social.web.handler.SecurityAccessDeniedHandler;
import com.ha.social.web.manager.SecurityAuthenticationManager;
import com.ha.social.web.util.SecurityUtils;

/**
 * Spring Security Configuration
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @see WebSecurityConfigurerAdapter
 * @since 7.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	/**
	 * User Service
	 */
	@Autowired
	private SecurityUserService securityUserService;

	/**
	 * 로그인 인증 서비스.
	 */
	@Autowired
	private SecurityAuthorizationService securityAuthorizationService;

	/**
	 * Override this method to configure {@link WebSecurity}. For example, if you
	 * wish to ignore certain requests.
	 *
	 * @param web the {@link WebSecurity} to modify
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		// 인증하지 않는 페이지 설정 - 주로 리소스 (Resources)
		web.ignoring().antMatchers(SecurityUtils.ignorePages());
	}

	/**
	 * Override this method to configure the {@link HttpSecurity}. Typically
	 * subclasses should not invoke this method by calling super as it may override
	 * their configuration. The default configuration is:
	 *
	 * <pre>
	 * http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
	 * </pre>
	 *
	 * @param http the {@link HttpSecurity} to modify
	 * @throws Exception if an error occurs
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Disable Cross site references
		http.csrf().disable() //

				// 사용자의 쿠키에 세션을 저장하지 않겠다는 옵션
				// NEVER의 경우에는 Security 등 내부적으로 세션을 만드는것을 허용한다. STATELESS를 써야 완벽한 무상태가 된다.
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //
				.and() //

				// .authorizeRequests().antMatchers("/login").permitAll() //
				// .and() //
				// .authorizeRequests().antMatchers("/signup").permitAll() //
				// .and() //
				.authorizeRequests().antMatchers("/**").hasAnyRole("ADMIN", "USER").anyRequest().authenticated() //
				.and() //

				// 인증 토큰 없이 요청 에러처리 - HTTP Status : 401 (Unauthorized)
				.exceptionHandling().authenticationEntryPoint(new SecurityAuthenticationEntryPoint()) //
				.and() //

				// 접근 권한 없이 접근하는 경우 에러 발생 - HTTP Status : 403 (Forbidden)
				.exceptionHandling().accessDeniedHandler(new SecurityAccessDeniedHandler()) //
				.and() //

				// POST 요청시에 한글이 깨지는 문제 보완 Filter 등록.
				.addFilterBefore(new CharacterEncodingFilter(), ChannelProcessingFilter.class)

				// // Allow Filter 등록.
				// .addFilterBefore(new AllowFilter(), ChannelProcessingFilter.class)

				// 로그인 확인 페이지 필터 등록.
				.addFilterBefore(generateTokenForUserFilter(), UsernamePasswordAuthenticationFilter.class)

				// 사용자의 인증 정보 조회 및 권한 설정 필터
				.addFilterBefore(sessionManagementFilter(), SessionManagementFilter.class);
	}

	/**
	 * 인증된 사용자와 관련된 모든 세션을 추적하는 필터 등록.
	 *
	 * @return the session management filter ({@link SecuritySessionManagementFilter})
	 */
	private SessionManagementFilter sessionManagementFilter() {
		return new SecuritySessionManagementFilter(securityAuthorizationService);
	}

	@Override
	public AuthenticationManager authenticationManager() {
		return new SecurityAuthenticationManager(securityUserService);
	}

	/**
	 * 인증 성공 Handler 등록.
	 *
	 * @return the authentication success handler ({@link GenerateAuthenticationSuccessHandler})
	 */
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new GenerateAuthenticationSuccessHandler(securityAuthorizationService);
	}

	/**
	 * 인증 실패 Handler 등록.
	 *
	 * @return the authentication failure handler ({@link GenerateAuthenticationFailureHandler})
	 */
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new GenerateAuthenticationFailureHandler();
	}

	/**
	 * 로그인 확인 페이지 필터 등록.
	 *
	 * <pre>
	 * <code>
	 * curl -X POST http://localhost:8080/login/submit      \
	 *      -H 'Content-Type: application/json'             \
	 * 	    -H 'cache-control: no-cache'                    \
	 * 	    -d '{"userid": "test", "passwd": "test1234"}'
	 * </code>
	 * </pre>
	 *
	 * @return the abstract authentication processing filter ({@link GenerateTokenForUserFilter})
	 */
	@Bean
	public AbstractAuthenticationProcessingFilter generateTokenForUserFilter() {
		return new GenerateTokenForUserFilter(Config.LOGIN_CHECK_PATH, authenticationManager(),
				authenticationSuccessHandler(), authenticationFailureHandler());
	}

}
