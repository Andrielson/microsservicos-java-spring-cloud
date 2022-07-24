package io.github.andrielson.hroauth.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.github.andrielson.hroauth.entities.User;
import io.github.andrielson.hroauth.feignclients.UserFeignClient;

@Service
public class UserService implements UserDetailsService {
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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = userFeignClient.findByEmail(username).getBody();
		if (user == null) {
			logger.error("Email not found: " + username);
			throw new UsernameNotFoundException("Email not found");
		}
		logger.info("Email found: " + username);
		return user;
	}
}
