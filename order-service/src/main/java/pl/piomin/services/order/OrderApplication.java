package pl.piomin.services.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import pl.piomin.services.messaging.Order;
import pl.piomin.services.order.repository.OrderRepository;
import pl.piomin.services.order.service.OrderService;

import java.util.function.Consumer;

@SpringBootApplication
public class OrderApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderApplication.class);
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	OrderService service;
	
	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
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
	
	@Bean
	OrderRepository repository() {
		return new OrderRepository();
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
