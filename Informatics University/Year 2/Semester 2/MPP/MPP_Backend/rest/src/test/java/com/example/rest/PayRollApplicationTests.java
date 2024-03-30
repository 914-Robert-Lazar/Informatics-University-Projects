package com.example.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.rest.Controller.Controller;

@SpringBootTest
class PayRollApplicationTests {

	@Autowired
	private Controller controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
