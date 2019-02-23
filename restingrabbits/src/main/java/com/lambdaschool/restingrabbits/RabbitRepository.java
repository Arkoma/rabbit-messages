package com.lambdaschool.restingrabbits;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RabbitRepository extends JpaRepository<Rabbit, Long>  {
}
