package com.lambdaschool.restingrabbits;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class RabbitMessage implements Serializable {

    private final String text;
    private final int priority;
    private final boolean secret;

    public RabbitMessage(@JsonProperty("text") String text,
                         @JsonProperty("priority") int priority,
                         @JsonProperty("secret") boolean secret) {
        this.text = text;
        this.priority = priority;
        this.secret = secret;
    }

    public String getText() {
        return text;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isSecret() {
        return secret;
    }

    @Override
    public String toString() {
        return "RabbitMessage{" +
                "text='" + text + '\'' +
                ", priority=" + priority +
                ", secret=" + secret +
                '}';
    }
}
