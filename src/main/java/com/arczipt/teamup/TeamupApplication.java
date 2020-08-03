package com.arczipt.teamup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class TeamupApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamupApplication.class, args);
	}

}
