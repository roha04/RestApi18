package com.example.restapinote;

import com.example.restapinote.controller.LoggingController;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RestapinoteApplicationTests {
	Logger logger = LoggerFactory.getLogger(LoggingController.class);
	@Test
	void contextLoads() {
		logger.info("Hello!");
	}
}
