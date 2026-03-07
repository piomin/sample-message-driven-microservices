package pl.piomin.services.order.messaging;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;
import pl.piomin.services.messaging.Order;

@Service
public class OrderSender {

	private final StreamBridge source;

    public OrderSender(StreamBridge source) {
        this.source = source;
    }

    public boolean send(Order order) {
         return this.source.send("output", MessageBuilder.withPayload(order).build());
    }
    
}
