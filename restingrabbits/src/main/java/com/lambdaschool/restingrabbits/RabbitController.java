package com.lambdaschool.restingrabbits;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Random;

@RestController
public class RabbitController {
    private final Logger log = LoggerFactory.getLogger(RabbitController.class);

    private final RabbitRepository repository;
    private final RabbitTemplate rt;

    public RabbitController(RabbitRepository repository, RabbitTemplate rt) {
        this.repository = repository;
        this.rt = rt;
    }

    @GetMapping("/rabbits")
    public void findSome() {
        ArrayList<Rabbit> rabbits = new ArrayList<>();
        rabbits.addAll(repository.findAll());

        for (Rabbit r: rabbits) {
            int priority = new Random().nextInt(10);
            boolean secret = new Random().nextBoolean();
            final RabbitMessage message = new RabbitMessage(r.toString(), priority, secret);

            log.info("Sending Message...");
            if (priority >= 5) {
                rt.convertAndSend(RestingrabbitsApplication.QUEUE_NAME_HIGH);
            } else {
                rt.convertAndSend(RestingrabbitsApplication.QUEUE_NAME_LOW);

            }
        }
    }
}
