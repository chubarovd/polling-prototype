package com.polling.polling_project.domain;

import javax.persistence.*;

@Entity
@Table (name="votes_table")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "user_id")
    private User author;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "item_id")
    private Item item;

    private Integer count;

    public Long getId () {
        return id;
    }
    public Vote setId (Long id) {
        this.id = id;
        return this;
    }

    public Integer getCount () {
        return count;
    }
    public Vote setCount (Integer count) {
        this.count = count;
        return this;
    }

    public User getAuthor () {
        return author;
    }
    public Vote setAuthor (User author) {
        this.author = author;
        return this;
    }

    public Item getItem () {
        return item;
    }
    public Vote setItem (Item item) {
        this.item = item;
        return this;
    }

    @Override
    public String toString() {
        return "id: ["+ id + "] " +
                "from: [" + author.getUsername() +
                "] content: [" + item.getContent() +
                "] votes: [" + count +
                "]\n";
    }
}
