package com.nothing.monolithic_quiz.dto;

import com.nothing.monolithic_quiz.entity.Question;
import lombok.Data;

@Data
public class QuestionDto {

    public QuestionDto(Integer id, String question, String option1, String option2, String option3, String option4) {
        this.id = id;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
    }

    private Integer id;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;

    public static QuestionDto fromEntity(Question q) {
        return new QuestionDto(q.getId(), q.getQuestion(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
    }


}
