package pl.piomin.services.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.piomin.services.order.repository.OrderRepository;

@Configuration
public class OrderConfig {

    @Bean
    OrderRepository repository() {
        return new OrderRepository();
    }

}
