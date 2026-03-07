package pl.piomin.services.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.piomin.services.account.service.AccountService;
import pl.piomin.services.messaging.Order;
import tools.jackson.databind.ObjectMapper;

import java.util.function.Consumer;

@SpringBootApplication
public class AccountApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountApplication.class);
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private final AccountService service;

	public AccountApplication(AccountService service) {
		this.service = service;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}

	@Bean
	public Consumer<Order> input() {
		return order -> {
			LOGGER.info("Order received: {}", mapper.writeValueAsString(order));
			service.process(order);
		};
	}
	
}
