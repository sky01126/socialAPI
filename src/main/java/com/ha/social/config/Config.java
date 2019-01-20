/*
 * Copyright ⓒ [2019] KTH corp.All rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */

package com.ha.social.config;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Config.java
 *
 * @author <a href="개발자 메일주소"><b>개발자명</b></a>
 * @version 1.0.0
 * @since 7.0
 */
public class Config {

	/** 인증 토큰명 - TODO 프로젝트에 맞게 수정 */
	public static final String AUTH_TOKEN = "X-Auth-Token";

	/** 암호화키 */
	public static final String SECRET_KEY = DigestUtils.sha1Hex("example.com");

	/** 로그인 확인 페이지 */
	public static final String LOGIN_CHECK_PATH = "/login/submit";

	/** TOKEN Expire Time (초단위) */
	public static final int TOKEN_EXPIRE = 365 * 24 * 60 * 60;

	/** 사용자 아이디 */
	public static final String USERID = "userid";

	/** 사용자명 */
	public static final String USERNAME = "username";

	/** 사용자 ROLE */
	public static final String ROLE = "role";

	private Config() {
		// ignore...
	}

}
