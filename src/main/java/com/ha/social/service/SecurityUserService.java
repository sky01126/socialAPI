/*
 * Copyright ⓒ [2017] KTH corp.All rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */

package com.ha.social.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.util.concurrent.AbstractService;
import com.ha.social.security.model.User;
import com.ha.social.security.model.UserToken;

/**
 * User Service
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @see AbstractService
 * @since 7.0
 */
@Service
public class SecurityUserService {

	private static final Logger log = LoggerFactory.getLogger(SecurityUserService.class);

	/**
	 * 사용자 정보 조회.
	 *
	 * @param userid 사용자 아이디.
	 * @param passwd 비밀번호.
	 * @return UserToken
	 */
	public UserToken getUserInfo(String userid, String passwd) {
		// TODO 사용자 정보 DB에서 조회
		User user = new User();
		user.setUserId(userid);
		user.setUserName("테스트");
		user.setAuthorities("ROLE_ADMIN,ROLE_USER");
		log.debug(user.toString());

		// 사용자 정보를 UserToken Model에 담는다.
		return new UserToken(user);
	}

}
