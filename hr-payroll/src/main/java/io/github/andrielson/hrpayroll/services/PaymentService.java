package io.github.andrielson.hrpayroll.services;

import org.springframework.stereotype.Service;

import io.github.andrielson.hrpayroll.entities.Payment;
import io.github.andrielson.hrpayroll.feignclients.WorkerFeignClient;

@Service
public class PaymentService {

	private final WorkerFeignClient workerFeignClient;

	public PaymentService(WorkerFeignClient workerFeignClient) {
		super();
		this.workerFeignClient = workerFeignClient;
	}

	public Payment getPayment(long workerId, int days) {
		var worker = workerFeignClient.findById(workerId).getBody();
		return new Payment(worker.getName(), worker.getDailyIncome(), days);
	}
}
