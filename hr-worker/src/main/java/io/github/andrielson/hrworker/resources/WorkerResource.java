package io.github.andrielson.hrworker.resources;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.andrielson.hrworker.entities.Worker;
import io.github.andrielson.hrworker.repositories.WorkerRepository;

@RefreshScope
@RestController
@RequestMapping(value = "/workers")
public class WorkerResource {
	private static final Logger logger = LoggerFactory.getLogger(WorkerResource.class);

	private final Environment env;
	private final WorkerRepository workerRepository;

	@Value("${test.config}")
	private String testConfig;

	public WorkerResource(Environment env, WorkerRepository workerRepository) {
		this.env = env;
		this.workerRepository = workerRepository;
	}

	@GetMapping(value = "/config")
	public ResponseEntity<Void> getConfig() {
		logger.info("CONFIG = " + testConfig);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<Worker>> findAll() {
		var list = workerRepository.findAll();
		return ResponseEntity.ok(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Worker> findById(@PathVariable Long id) {
		logger.info("PORT = " + env.getProperty("local.server.port"));
		var worker = workerRepository.findById(id).get();
		return ResponseEntity.ok(worker);
	}
}
