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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 사용자 정보
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @see Serializable
 * @since 7.0
 */
@Component
@SuppressWarnings("all")
public class User implements Serializable {

	/**
	 * 사용자 아이디
	 */
	private String userId;

	/**
	 * 사용자 비밀번호
	 */
	private String passwd;

	/**
	 * 사용자명
	 */
	private String userName;

	/**
	 * 권한.
	 */
	private List<String> authorities;

	public User() {
		// ignore...
	}

	/**
	 * 사용자 아이디
	 *
	 * @return the user iId
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * 사용자 아이디
	 *
	 * @param id the userId to set
	 */
	public void setUserId(String id) {
		this.userId = id;
	}

	/**
	 * 사용자 비밀번호
	 *
	 * @return the password
	 */
	public String getPasswd() {
		return this.passwd;
	}

	/**
	 * 사용자 비밀번호
	 *
	 * @param passwd the password to set
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	/**
	 * 사용자명
	 *
	 * @return the user name
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * 사용자명
	 *
	 * @param name the user name to set
	 */
	public void setUserName(String name) {
		this.userName = name;
	}

	/**
	 * TODO 권한 조회
	 *
	 * @return the user authorits
	 */
	public List<String> getAuthorities() {
		if (this.authorities == null || this.authorities.isEmpty()) {
			new ArrayList<>().add("ROLE_ANONYMOUS");
		}
		return authorities;
	}

	/**
	 * 권한 설정
	 *
	 * @return the user authorits
	 */
	public void setAuthorities(List<String> authorities) {
		if (authorities == null || authorities.isEmpty()) {
			authorities = new ArrayList<>();
			authorities.add("ROLE_ANONYMOUS");
		}
		this.authorities = authorities;
	}

	/**
	 * 권한.
	 *
	 * @param authorities the authorities to set
	 */
	public void setAuthorities(String authorities) {
		List<String> list = new ArrayList<>();
		if (StringUtils.isNotBlank(authorities)) {
			list = this.authorities = Lists.newArrayList(Sets.newHashSet(StringUtils.split(authorities, ",")));
		}
		list.add("ROLE_ANONYMOUS");
		this.authorities = list;
	}

	/**
	 * 권한 List
	 *
	 * @return the authority list
	 */
	public List<GrantedAuthority> getAuthorityList() {
		if (this.authorities == null || this.authorities.isEmpty()) {
			return null;
		}
		return AuthorityUtils.createAuthorityList(this.authorities.toArray(new String[0]));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [userId=");
		builder.append(userId);
		builder.append(", passwd=[PROTECTED]");
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", authorities=");
		builder.append(authorities);
		builder.append("]");
		return builder.toString();
	}

}
