package io.github.andrielson.hrpayroll.services;

import org.springframework.stereotype.Service;

import io.github.andrielson.hrpayroll.entities.Payment;

@Service
public class PaymentService {
	
	public Payment getPayment(long workerId, int days) {
		return new Payment("Lorem Ipsum", 200.0, days);
	}
}
