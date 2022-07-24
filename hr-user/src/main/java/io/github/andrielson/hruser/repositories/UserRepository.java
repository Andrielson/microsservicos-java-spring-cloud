package io.github.andrielson.hruser.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.andrielson.hruser.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
}
