
package com.sharath.product_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharath.product_service.dto.ProductRequest;
import com.sharath.product_service.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class ProductServiceApplicationTests {

	@Container
	static PostgreSQLContainer<?> postgresContainer =
			new PostgreSQLContainer<>("postgres:17")
					.withDatabaseName("productdb")
					.withUsername("postgres")
					.withPassword("postgres");

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProductRepository productRepository;

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {

		registry.add(
				"spring.datasource.url",
				postgresContainer::getJdbcUrl);

		registry.add(
				"spring.datasource.username",
				postgresContainer::getUsername);

		registry.add(
				"spring.datasource.password",
				postgresContainer::getPassword);

		registry.add(
				"spring.datasource.driver-class-name",
				postgresContainer::getDriverClassName);
	}

	@Test
	void shouldCreateProduct() throws Exception {

		ProductRequest productRequest = ProductRequest.builder()
				.name("iPhone 13")
				.description("iPhone 13")
				.price(BigDecimal.valueOf(1200))
				.build();

		mockMvc.perform(post("/api/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(productRequest)))
				.andExpect(status().isCreated());

		Assertions.assertEquals(1,
				productRepository.findAll().size());
	}
}
