/*
* Copyright ⓒ [2017] KTH corp.All rights reserved.
*
* This is a proprietary software of KTH corp, and you may not use this file except in
* compliance with license with license agreement with KTH corp. Any redistribution or use of this
* software, with or without modification shall be strictly prohibited without prior written
* approval of KTH corp, and the copyright notice above does not evidence any actual or
* intended publication of such software.
*/

package com.ha.social.web.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Custom Error Controller
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @see ErrorController
 * @since 7.0
 */
@Controller
public class CustomErrorController implements ErrorController {

	@RequestMapping("/error")
	public ResponseEntity<Object> handleError(HttpServletRequest request) {
		// TODO 에러 페이지로 대체.
		int status = (int) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("result_code", status);
		map.put("result_msg", HttpStatus.valueOf(status).getReasonPhrase());
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}

}
