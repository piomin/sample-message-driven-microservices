package pl.piomin.services.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit4.SpringRunner;

import pl.piomin.services.messaging.Order;
import pl.piomin.services.messaging.OrderStatus;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderReceiverTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderReceiverTest.class);
	
	@Autowired
	private Processor processor;
	@Autowired
	private MessageCollector messageCollector;

	@Test
	@SuppressWarnings("unchecked")
	public void testAccepted() {
		Order o = new Order();
		o.setId(1L);
		o.setAccountId(1L);
		o.setCustomerId(1L);
		o.setPrice(500);
		o.setProductIds(Collections.singletonList(2L));
		processor.input().send(MessageBuilder.withPayload(o).build());
		Message<Order> received = (Message<Order>) messageCollector.forChannel(processor.output()).poll();
		LOGGER.info("Order response received: {}", received.getPayload());
		assertNotNull(received.getPayload());
		assertEquals(OrderStatus.ACCEPTED, received.getPayload().getStatus());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testRejected() {
		Order o = new Order();
		o.setId(1L);
		o.setAccountId(1L);
		o.setCustomerId(1L);
		o.setPrice(100000);
		o.setProductIds(Collections.singletonList(2L));
		processor.input().send(MessageBuilder.withPayload(o).build());
		Message<Order> received = (Message<Order>) messageCollector.forChannel(processor.output()).poll();
		LOGGER.info("Order response received: {}", received.getPayload());
		assertNotNull(received.getPayload());
		assertEquals(OrderStatus.REJECTED, received.getPayload().getStatus());
	}

}
