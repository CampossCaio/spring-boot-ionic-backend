package com.caiocampos.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.caiocampos.cursomc.services.DBservice;
import com.caiocampos.cursomc.services.EmailService;
import com.caiocampos.cursomc.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBservice dbService;

	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.instanciateTesteDatabase();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}

}
