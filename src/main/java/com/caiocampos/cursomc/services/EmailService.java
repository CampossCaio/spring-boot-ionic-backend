package com.caiocampos.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.caiocampos.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
		

}
