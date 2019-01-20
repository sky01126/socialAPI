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
