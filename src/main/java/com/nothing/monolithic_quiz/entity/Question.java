package com.nothing.monolithic_quiz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qid", nullable = false)
    private Integer id;

    @Column(name = "category")
    private String category;

    @Column(name = "difficulty", length = 20)
    private String difficulty;

    @Column(name = "option1", length = 50)
    private String option1;

    @Column(name = "option2", length = 50)
    private String option2;

    @Column(name = "option3", length = 50)
    private String option3;

    @Column(name = "option4", length = 50)
    private String option4;

    @Column(name = "question", length = 100)
    private String question;

    @Column(name = "answer", length = 50)
    private String answer;

}