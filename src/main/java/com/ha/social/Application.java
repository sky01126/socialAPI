package com.ha.social;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	/**
	 * Spring boot application main method.
	 *
	 * @param args the string array
	 */
	public static void main(String[] args) {
		log.info("Application start...");
		SpringApplication.run(Application.class, args);
	}

	/**
	 * Configure the application. Normally all you would need to do is to add
	 * sources (e.g. config classes) because other settings have sensible defaults.
	 * You might choose (for instance) to add default command line arguments, or set
	 * an active Spring profile.
	 *
	 * @param application a builder for the application context
	 * @return the spring application builder
	 * @see SpringApplicationBuilder
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
}
