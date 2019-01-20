/*
 * Copyright(C) 2017 KT Hitel Co., Ltd. all rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */

package com.ha.social;

import java.nio.charset.StandardCharsets;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.WebJarsResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * Web Mvc Configuration.
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @see WebMvcConfigurer
 * @since 7.0
 */
@EnableWebMvc
@ComponentScan
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

	private static final Logger log = LoggerFactory.getLogger(WebMvcConfiguration.class);

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		log.debug("Add Resource Handlers...");

		registry.setOrder(Integer.MIN_VALUE);
		registry.addResourceHandler("/favicon.ico") // Resource Handler
				.addResourceLocations("/") // Resource Locations
				.setCachePeriod(0); // Cache 기간 설정

		// Static Resource 등록.
		registry.addResourceHandler("/static/**") // Resource Handler
				.addResourceLocations("classpath:/static/") // Resource Locations
				// .setCachePeriod(31556926); // Cache 기간 설정
				.resourceChain(true) // Resource Chain True
				.addResolver(new PathResourceResolver());

		// webjars를 Resource 등록.
		// webjars의 정확한 버전을 몰라도 사용할 수 있도록한다. ex. /webjars/jquery/jquery.min.js
		registry.addResourceHandler("/webjars/**") // Resource Handler
				.addResourceLocations("classpath:/META-INF/resources/webjars/") // Resource Locations
				// .setCachePeriod(31556926) // Cache 기간 설정
				.resourceChain(true) // Resource Chain True
				.addResolver(new WebJarsResourceResolver());

		// Cross Domain 등록.
		registry.addResourceHandler("/crossdomain.xml") // Resource Handler
				.addResourceLocations("/crossdomain.xml"); // Resource Locations
		// .setCachePeriod(31556926); // Cache 기간 설정

	}

	/**
	 * Configure cross origin requests processing.
	 *
	 * @param registry the view resolver registry
	 * @since Spring Framework 4.1
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.viewResolver(viewResolver());
	}

	/**
	 * Configure View resolver to provide HTML output This is the default format in
	 * absence of any type suffix.
	 *
	 * @return ViewResolver the internal resource view resolver
	 */
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	/**
	 * 결과를 출력시에 강제로 UTF-8로 설정
	 *
	 * @return HttpMessageConverter
	 */
	@Bean
	public HttpMessageConverter<String> responseBodyConverter() {
		log.debug("Response Body Converter 등록...");
		return new StringHttpMessageConverter(StandardCharsets.UTF_8);
	}

	// --------------------------------------------- Add Filter
	/**
	 * POST 요청시에 한글이 깨지는 문제 보완
	 *
	 * @return FilterRegistrationBean
	 */
	@Bean
	public FilterRegistrationBean<Filter> characterEncodingFilter() {
		log.debug("Character Encoding Filter 등록...");
		final CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding(StandardCharsets.UTF_8.name());
		characterEncodingFilter.setForceEncoding(true);

		final FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>(characterEncodingFilter);
		registration.setDispatcherTypes(DispatcherType.REQUEST);
		registration.setOrder(Integer.MIN_VALUE);
		return registration;
	}

}
