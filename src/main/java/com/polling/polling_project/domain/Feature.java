package com.polling.polling_project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "features")
@Builder
@AllArgsConstructor
@Getter
@Setter
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "feature_category",
            joinColumns = { @JoinColumn(name = "feature_id") },
            inverseJoinColumns = { @JoinColumn(name = "category_id") }
    )
    private Set<Category> categories;

    @Enumerated(EnumType.STRING)
    private EVoteStatus status;

    private String title;
    private String description;
    private int starsCount;
    private int votesCount;
    private boolean voted;
}
