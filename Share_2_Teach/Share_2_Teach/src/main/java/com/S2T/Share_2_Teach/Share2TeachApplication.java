package com.S2T.Share_2_Teach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.S2T.Share_2_Teach")
public class Share2TeachApplication {

	public static void main(String[] args) {

		SpringApplication.run(Share2TeachApplication.class, args);
	}

}
