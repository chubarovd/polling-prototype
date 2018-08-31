package com.polling.polling_project.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "oldvotes")
@Builder
@Getter
public class OldVote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    private String content;
    private Integer count;

    @Override
    public String toString() {
        return "id: [" + this.id + "] " +
                "from: [" + this.author.getUsername() +
                "] content: [" + this.content +
                "] votes: [" + this.count +
                "]\n";
    }
}
