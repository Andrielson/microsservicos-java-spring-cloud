package io.github.andrielson.hroauth.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.github.andrielson.hroauth.entities.User;
import io.github.andrielson.hroauth.feignclients.UserFeignClient;

@Service
public class UserService {
	private final Logger logger = LoggerFactory.getLogger(UserService.class);

	private final UserFeignClient userFeignClient;

	public UserService(UserFeignClient userFeignClient) {
		super();
		this.userFeignClient = userFeignClient;
	}

	public User findByEmail(String email) {
		var user = userFeignClient.findByEmail(email).getBody();
		if (user == null) {
			logger.error("Email not found: " + email);
			throw new IllegalArgumentException("Email not found");
		}
		logger.info("Email found: " + email);
		return user;
	}
}
