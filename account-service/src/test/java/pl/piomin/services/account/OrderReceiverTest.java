package pl.piomin.services.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import pl.piomin.services.messaging.OrderStatus;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderReceiverTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderReceiverTest.class);

	@Autowired
	private InputDestination input;
	@Autowired
	private OutputDestination output;
	@Autowired
	private ObjectMapper mapper;

	@Test
	public void testAccepted() throws JsonProcessingException {
		Order o = new Order();
		o.setId(1L);
		o.setAccountId(1L);
		o.setCustomerId(1L);
		o.setPrice(500);
		o.setProductIds(Collections.singletonList(2L));
		input.send(MessageBuilder.withPayload(o).build());
		Message<byte[]> received = output.receive();
		assertNotNull(received.getPayload());
		String json = new String(received.getPayload());
		LOGGER.info("Order response received: {}", json);

		o = mapper.readValue(json, Order.class);
		assertEquals(OrderStatus.ACCEPTED, o.getStatus());
	}
	
	@Test
	public void testRejected() throws JsonProcessingException {
		Order o = new Order();
		o.setId(1L);
		o.setAccountId(1L);
		o.setCustomerId(1L);
		o.setPrice(100000);
		o.setProductIds(Collections.singletonList(2L));
		input.send(MessageBuilder.withPayload(o).build());
		Message<byte[]> received = output.receive();
		assertNotNull(received.getPayload());
		String json = new String(received.getPayload());
		LOGGER.info("Order response received: {}", json);

		o = mapper.readValue(json, Order.class);
		assertEquals(OrderStatus.REJECTED, o.getStatus());
	}

}
