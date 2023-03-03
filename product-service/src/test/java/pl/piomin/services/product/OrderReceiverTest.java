package pl.piomin.services.product;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import pl.piomin.services.messaging.Order;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderReceiverTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderReceiverTest.class);

	@Autowired
	private InputDestination input;
	@Autowired
	private OutputDestination output;

	@Test
	public void testProcessing() {
		Order o = new Order();
		o.setId(1L);
		o.setAccountId(1L);
		o.setCustomerId(1L);
		o.setPrice(500);
		o.setProductIds(Collections.singletonList(2L));
		input.send(MessageBuilder.withPayload(o).build());
		Message<byte[]> received = output.receive();
		LOGGER.info("Order response received: {}", new String(received.getPayload()));
		assertNotNull(received.getPayload());
//		assertEquals(OrderStatus.ACCEPTED, received.getPayload().getStatus());
	}

}
