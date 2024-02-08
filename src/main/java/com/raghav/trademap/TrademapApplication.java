package com.raghav.trademap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.raghav.trademap"})
@ComponentScan({"com.raghav.trademap"})
@EntityScan(basePackages = "com.raghav.trademap")
public class TrademapApplication
//		extends SpringBootServletInitializer
{

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(TrademapApplication.class);
//	}

	public static void main(String[] args) {
		SpringApplication.run(TrademapApplication.class, args);
	}

}
