package com.polling.polling_project.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "votes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Item item;

    private Integer count;

    @Override
    public String toString() {
        return "id: [" + id + "] " +
                "from: [" + author.getUsername() +
                "] content: [" + item.getContent() +
                "] votes: [" + count +
                "]\n";
    }
}
