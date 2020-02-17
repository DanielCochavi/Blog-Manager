package com.daniel.rest.microblog.content;

import javax.persistence.Embeddable;

@Embeddable
public class Content {

    private String text;

    public Content() {}

    public Content(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String test) {
        this.text = test;
    }
}
