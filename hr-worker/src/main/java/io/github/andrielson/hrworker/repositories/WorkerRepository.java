package io.github.andrielson.hrworker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.andrielson.hrworker.entities.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

}
