package com.daniel.rest.microblog.post;

import com.daniel.rest.microblog.content.Content;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private Content content;
    @NonNull
    private String username;
    @NonNull
    private Integer numOfLikes;
    @NonNull
    private Date createdTime;

    public Post() {
    }

    public Post(Long id, Content content, String username, Integer numOfLikes, Date createdTime) {
        this.id = id;
        this.content = content;
        this.username = username;
        this.numOfLikes = numOfLikes;
        this.createdTime = createdTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if(username != null) {
            this.username = username;
        }
        else{
            this.username = "-Anonymous-";
        }
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Integer getNumOfLikes() {
        return numOfLikes;
    }

    public void setNumOfLikes(Integer numOfLikes) {
        if(numOfLikes != null) {
            this.numOfLikes = numOfLikes;
        }
        else{
            this.numOfLikes = 0;
        }
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        if(createdTime != null) {
            this.createdTime = createdTime;
        }
        else{
            this.createdTime = new Date();
        }
    }

}
