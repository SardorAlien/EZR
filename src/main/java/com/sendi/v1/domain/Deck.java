package com.sendi.v1.domain;

import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "decks")
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany
    private Flashcard flashcard;

    //todo This should be User object
    @Column(name = "user_id")
    private Integer userId;
}
