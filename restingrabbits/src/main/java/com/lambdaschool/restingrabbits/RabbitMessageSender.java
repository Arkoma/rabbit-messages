package com.lambdaschool.restingrabbits;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.Random;

//@Service
public class RabbitMessageSender {
    private final Logger log = LoggerFactory.getLogger(RabbitMessageSender.class);
    private final RabbitTemplate rt;
    private final RabbitRepository repository;

    public RabbitMessageSender(RabbitTemplate rt, RabbitRepository repository) {
        this.rt = rt;
        this.repository = repository;
    }

    @Scheduled(fixedDelay = 3000L)
    public void sendMessage() {
        ArrayList<Rabbit> rabbits = new ArrayList<>();

        rabbits.addAll(repository.findAll());

        for (Rabbit r: rabbits) {
            int priority = new Random().nextInt(10);
            boolean secret = new Random().nextBoolean();
            final RabbitMessage message = new RabbitMessage(r.toString(), priority, secret);
            // do not send out priority 7 messages
            // front - end
            // middleware - business application, requirements
            // back - end
            if (priority <= 5) {
                log.info("Sending Message HIGH");
                rt.convertAndSend(RestingrabbitsApplication.QUEUE_NAME_HIGH, message);
            } else {
                log.info("Sending Message LOW");
                rt.convertAndSend(RestingrabbitsApplication.QUEUE_NAME_LOW, message);
            }
        }
    }
}
