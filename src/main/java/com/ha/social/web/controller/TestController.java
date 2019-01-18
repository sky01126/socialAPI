package com.ha.social.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = { "/test" }, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class TestController {

	private static final Logger log = LoggerFactory.getLogger(TestController.class);

	/**
	 * GET
	 *
	 * @param request  the http servlet request
	 * @param response the http servlet response
	 * @return Response DTO(Data Transfer Object)
	 */
	@GetMapping(path = "get")
	public ResponseEntity<Object> doGet(HttpServletRequest request, HttpServletResponse response) {
		log.debug("Test controller - test get");
		Map<String, Object> map = new HashMap<>();
		map.put("result_code", 200);
		map.put("result_msg", "SUCCESS");
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
}
