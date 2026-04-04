package com.nothing.quizservice.feing;

import com.nothing.quizservice.dto.QuestionDto;
import com.nothing.quizservice.entity.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {
    //generate
    @GetMapping("/question/generate")
    public ResponseEntity<List<Integer>> generateQuestions(@RequestParam String category, @RequestParam int numq);

    //get question
    @PostMapping("/question/getQuestions")
    public ResponseEntity<List<QuestionDto>> getQuestionsFromId(@RequestBody List<Integer> question);

    //calculate score
    @PostMapping("/question/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);
}
