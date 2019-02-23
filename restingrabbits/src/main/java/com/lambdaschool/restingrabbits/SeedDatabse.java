package com.lambdaschool.restingrabbits;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeedDatabse {
    final Logger log = LoggerFactory.getLogger(SeedDatabse.class);

    @Bean
    public CommandLineRunner initDB(RabbitRepository repository) {
        return args -> {
            log.info("Seeding " + repository.save(new Rabbit("Barn Barn", 5.1)));
            log.info("Seeding " + repository.save(new Rabbit("Cinnamon", 4.3)));
            log.info("Seeding " + repository.save(new Rabbit("Jessica", 4.7)));
        };
    }
}
