package com.online.market.orderservice.processor;

import com.online.market.orderservice.entity.OutboxEvent;
import com.online.market.orderservice.repository.OutboxEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutboxProcessor {

    @Autowired
    private OutboxEventRepository outboxEventRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedRate = 5000) // Run every 5 seconds
    public void processOutboxEvents() {
        System.out.println("run");
        List<OutboxEvent> outboxEvents = outboxEventRepository.findAll();

        for (OutboxEvent outboxEvent : outboxEvents) {
            // Publish events to Kafka
            kafkaTemplate.send("order-events", outboxEvent.getEventType(), outboxEvent.getEventPayload());

            // Remove the event from the outbox
            outboxEventRepository.delete(outboxEvent);
        }
        System.out.println("finish");
    }

}
