package pl.piomin.services.product;

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
import pl.piomin.services.product.service.ProductService;

import java.util.function.Consumer;

@SpringBootApplication
public class ProductApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductApplication.class);
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	ProductService service;
	
	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
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
	public CommonsRequestLoggingFilter requestLoggingFilter() {
	    CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
	    loggingFilter.setIncludePayload(true);
	    loggingFilter.setIncludeHeaders(true);
	    loggingFilter.setMaxPayloadLength(1000);
	    loggingFilter.setAfterMessagePrefix("REQ:");
	    return loggingFilter;
	}

}
