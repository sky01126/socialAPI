/*
 * Copyright ⓒ [2017] KTH corp.All rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */

package com.ha.social.security.model;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * User Authentication
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @see Authentication
 * @since 7.0
 */
public class UserAuthentication implements Authentication {

	private static final long serialVersionUID = 6570858707969532934L;

	private final UserToken token;

	private boolean authenticated = true;

	public UserAuthentication(UserToken token) {
		this.token = token;
	}

	@Override
	public String getName() {
		return token.getUsername();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return token.getAuthorities();
	}

	@Override
	public Object getCredentials() {
		return token.getPassword();
	}

	@Override
	public UserToken getDetails() {
		return token;
	}

	@Override
	public Object getPrincipal() {
		return token.getUsername();
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) {
		this.authenticated = isAuthenticated;
	}

	/**
	 * 사용자의 상세 정보.
	 *
	 * @return User
	 */
	public User getUser() {
		return token.getUser();
	}

}
