package pl.piomin.services.order;

import tools.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import pl.piomin.services.messaging.Order;
import pl.piomin.services.order.service.OrderService;

import java.util.function.Consumer;

@SpringBootApplication
public class OrderApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderApplication.class);
	private ObjectMapper mapper = new ObjectMapper();
	
	private final OrderService service;

	public OrderApplication(OrderService service) {
		this.service = service;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

	@Bean
	public Consumer<Order> input() {
		return order -> {
			LOGGER.info("Order received: {}", mapper.writeValueAsString(order));
			service.process(order);
		};
	}

	@Bean
	public CommonsRequestLoggingFilter requestLoggingFilter() {
	    CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
	    loggingFilter.setIncludePayload(true);
	    loggingFilter.setIncludeHeaders(true);
	    loggingFilter.setMaxPayloadLength(1000);
	    loggingFilter.setAfterMessagePrefix("REQ:");
	    return loggingFilter;
	}
	
}
