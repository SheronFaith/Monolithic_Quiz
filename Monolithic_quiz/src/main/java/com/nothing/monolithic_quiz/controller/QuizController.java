package com.nothing.monolithic_quiz.controller;

import com.nothing.monolithic_quiz.dto.QuestionDto;
import com.nothing.monolithic_quiz.entity.Response;
import com.nothing.monolithic_quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    public QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestParam String category
                                            ,@RequestParam int numq
                                            ,@RequestParam String title){


        return quizService.createQuiz(category,numq,title);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionDto>> getQuizQuestions(@PathVariable int id){

    return quizService.getQuizQuestions(id);
    }


    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable int id, @RequestBody List<Response> response){
        return quizService.calculaateResult(id,response);
    }

}
