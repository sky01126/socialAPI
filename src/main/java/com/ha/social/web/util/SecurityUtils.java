/*
 * Copyright ⓒ [2017] KTH corp.All rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */

package com.ha.social.web.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Security Util
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @since 7.0
 */
public class SecurityUtils {

	private SecurityUtils() {
		// ignore...
	}

	/**
	 * 접근 권한을 무시할 페이지 설정.
	 *
	 * @return Page URL List
	 */
	public static final String[] ignorePages() {
		List<String> list = new ArrayList<>();
		list.add("/html/**");
		list.add("/static/**");
		list.add("/webjars/**");
		list.add("/WEB-INF/**");
		list.add("/crossdomain.xml");
		list.add("/**.jpe*g");
		list.add("/**.gif");
		list.add("/**.png");
		list.add("/**.bmp");
		list.add("/**.ico");
		list.add("/**.css");
		list.add("/**.js");
		list.add("/**.ttf");
		list.add("/**.otf");
		list.add("/**.eot");
		list.add("/**.woff");
		list.add("/**.woff2");
		list.add("/**.pdf");
		list.add("/**.zip");
		list.add("/**.htm");
		list.add("/**.html");
		list.add("/**.docx*");
		list.add("/**.xlsx*");
		list.add("/**.pptx*");
		list.add("/**.txt");
		list.add("/**.wav");
		list.add("/**.swf");
		list.add("/**.svg");
		list.add("/**.avi");
		list.add("/**.mp");
		list.add("/error");

		list.add("/login"); // 로그인 아이디 / 비밀번호 입력 페이지
		list.add("/login/submit"); // 로그인 아이디 / 비밀번호 검증 API

		return list.toArray(new String[0]);
	}

}
