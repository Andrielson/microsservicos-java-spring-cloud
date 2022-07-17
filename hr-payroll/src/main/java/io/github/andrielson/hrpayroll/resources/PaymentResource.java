package io.github.andrielson.hrpayroll.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.github.andrielson.hrpayroll.entities.Payment;
import io.github.andrielson.hrpayroll.services.PaymentService;

@RestController
@RequestMapping(value = "/payments")
public class PaymentResource {
	private final PaymentService paymentService;

	public PaymentResource(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@HystrixCommand(fallbackMethod = "getPaymentAlternative")
	@GetMapping(value = "/{workerId}/days/{days}")
	public ResponseEntity<Payment> getPayment(@PathVariable Long workerId, @PathVariable Integer days) {
		var payment = paymentService.getPayment(workerId, days);
		return ResponseEntity.ok(payment);
	}

	public ResponseEntity<Payment> getPaymentAlternative(Long workerId, Integer days) {
		var payment = new Payment("Lorem Ipsum", 400.0, days);
		return ResponseEntity.ok(payment);
	}
}
