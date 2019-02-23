package com.lambdaschool.restingrabbits;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

@Service
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
//        var rabbits = new ArrayList<Rabbit>();

        rabbits.addAll(repository.findAll());

        for (Rabbit r : rabbits) {
            int priority = new Random().nextInt(10);
            boolean secret = new Random().nextBoolean();
            final RabbitMessage message = new RabbitMessage(r.toString(), priority, secret);
            if (priority <= 5) {
                rt.convertAndSend(RestingrabbitsApplication.QUEUE_NAME_HIGH, message);
            } else {
                rt.convertAndSend(RestingrabbitsApplication.QUEUE_NAME_LOW, message);
            }
        }
    }
}
