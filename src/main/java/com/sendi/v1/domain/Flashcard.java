package com.sendi.v1.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "flashcards")
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "term")
    private String term;

    @Column(name = "definition")
    private String definition;
}
