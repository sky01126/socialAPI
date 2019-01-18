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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

/**
 * 어플리케이션의 Lift Cycle에 따른 처리.
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @see InitializingBean
 * @see DisposableBean
 * @since 7.0
 */
@Configuration
public class LifecycleConfiguration implements InitializingBean, DisposableBean {

	private static final Logger log = LoggerFactory.getLogger(LifecycleConfiguration.class);

	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("[ START ] Spring Boot 시작 시 처리할 내용 추가");
	}

	@Override
	public void destroy() throws Exception {
		log.info("[ STOP  ] Spring Boot 종료 시 처리할 내용 추가");
	}

}
