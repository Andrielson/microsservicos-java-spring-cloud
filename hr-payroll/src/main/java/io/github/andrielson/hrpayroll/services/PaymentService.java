package io.github.andrielson.hrpayroll.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.andrielson.hrpayroll.entities.Payment;
import io.github.andrielson.hrpayroll.entities.Worker;

@Service
public class PaymentService {

	private final RestTemplate restTemplate;

	@Value("${hr-worker.host}")
	private String workerHost;

	public PaymentService(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

	public Payment getPayment(long workerId, int days) {
		var uriVariables = Map.of("id", String.valueOf(workerId));
		var worker = restTemplate.getForObject(workerHost + "/workers/{id}", Worker.class, uriVariables);
		return new Payment(worker.getName(), worker.getDailyIncome(), days);
	}
}
