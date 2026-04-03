package com.nothing.monolithic_quiz.service;

import com.nothing.monolithic_quiz.entity.Question;
import com.nothing.monolithic_quiz.repository.QuestionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    public QuestionRepository questionRepository;
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }
    public ResponseEntity<List<Question>> getAllQuestions() {
        try{
            return  new ResponseEntity<>(questionRepository.findAll(),HttpStatus.OK);
        }catch (Exception e){
            e.getStackTrace();
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try{
            return new ResponseEntity<>(questionRepository.findByCategoryIgnoreCase(category),HttpStatus.OK);
        }catch (Exception e){
            e.getStackTrace();
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try{
            questionRepository.save(question);
        }catch (Exception e){
            e.getStackTrace();
            return new ResponseEntity<>("Failed",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Success",HttpStatus.CREATED);
    }
}
