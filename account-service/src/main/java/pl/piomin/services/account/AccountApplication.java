package pl.piomin.services.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.piomin.services.account.service.AccountService;
import pl.piomin.services.messaging.Order;

import java.util.function.Consumer;

@SpringBootApplication
public class AccountApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountApplication.class);
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	AccountService service;
	
	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}

	@Bean
	public Consumer<Order> input() {
		return order -> {
			try {
				LOGGER.info("Order received: {}", mapper.writeValueAsString(order));
				service.process(order);
			} catch (JsonProcessingException e) {
				LOGGER.error("Error deserializing", e);
			}
		};
	}

//	@Bean
//	public Sampler defaultSampler() {
//		return new AlwaysSampler();
//	}
	
}
