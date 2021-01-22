package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
public class ReglasApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReglasApiApplication.class, args);
	}

}
