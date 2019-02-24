package com.lambdaschool.restingrabbits;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMessageListener {
    private final Logger log = LoggerFactory.getLogger(RabbitMessageListener.class);
    @RabbitListener(queues = RestingrabbitsApplication.QUEUE_NAME_HIGH)
    public void receiveMessage(RabbitMessage rm) {
        log.info("Recieved Message: {}", rm.toString());
    }
}
