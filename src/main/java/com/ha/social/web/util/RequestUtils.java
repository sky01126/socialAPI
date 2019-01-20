/*
 * Copyright(C) 2017 KT Hitel Co., Ltd. all rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */
package com.ha.social.web.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTTP Request 시 사용될 유틸리티
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @since 7.0
 */
public class RequestUtils {

	private static final Logger log = LoggerFactory.getLogger(RequestUtils.class);

	private RequestUtils() {
		// ignore..
	}

	/**
	 * 확장자를 이용해서 Resource 여부 확인.
	 *
	 * @param str 비교할 String
	 * @return true / false
	 */
	public static final boolean isResource(final String str) {
		log.debug("String: {}", str);
		if (StringUtils.isBlank(str)) {
			return false;
		}
		String allowPattern = ".+\\.(jpe?g|gif|png|bmp|ico|css|js|ttf|otf|eot|woff|woff2|pdf|zip|htm|html|docx?|xlsx?|pptx?|txt|wav|swf|svg|avi|mp\\d)$";
		Pattern pattern = Pattern.compile(allowPattern);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

}
