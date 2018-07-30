package com.polling.polling_project.domain;

import javax.persistence.*;

@Entity
@Table (name="item_table")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;

    public Long getId () {
        return id;
    }
    public Item setId (Long id) {
        this.id = id;
        return this;
    }

    public String getContent () {
        return content;
    }
    public Item setContent (String content) {
        this.content = content;
        return this;
    }

    @Override
    public String toString(){
        return this.content;
    }
}
