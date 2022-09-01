package com.mouzetech.mouzefoodapi;

import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.mouzetech.mouzefoodapi.domain.infrastructure.repository.CustomRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomRepositoryImpl.class)
public class MouzefoodApiApplication implements CommandLineRunner {

	@Autowired
	private ApplicationContext applicationContext;
	
	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(MouzefoodApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		String[] beans = applicationContext.getBeanDefinitionNames();
//		Arrays.stream(beans).forEach(System.out::println);
	}
}