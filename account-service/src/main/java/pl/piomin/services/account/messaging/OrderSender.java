package pl.piomin.services.account.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;
import pl.piomin.services.messaging.Order;

@Service
public class OrderSender {

	@Autowired
    private StreamBridge source;

    public boolean send(Order order) {
         return this.source.send("output", MessageBuilder.withPayload(order).build());
    }
    
}
