package org.ting.spring.model;

import lombok.Data;

@Data
public class Greeting {

    private String content;

    public Greeting(String content) {
        this.content = content;
    }

    public Greeting(){}
}
