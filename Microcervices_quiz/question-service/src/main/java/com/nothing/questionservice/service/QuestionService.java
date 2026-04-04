package com.nothing.questionservice.service;

import com.nothing.questionservice.dto.QuestionDto;
import com.nothing.questionservice.entity.Question;
import com.nothing.questionservice.entity.Response;
import com.nothing.questionservice.repository.QuestionRepository;
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

    public ResponseEntity<String> deleteQuestion(int id) {
        try{
            questionRepository.deleteById(id);
            return new ResponseEntity<>("Success",HttpStatus.OK);
        }catch (Exception e){
            e.getStackTrace();
            return new ResponseEntity<>("Failed",HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Question> getQuestionById(int id) {
        return new ResponseEntity<>(questionRepository.findById(id).orElseThrow(()-> new RuntimeException("Question not found: "+id)),HttpStatus.OK);
    }

    public ResponseEntity<String> updateQuestion(Question question) {
        try {
            questionRepository.save(question);
        }catch (Exception e){
            e.getStackTrace();
            return new ResponseEntity<>("Failed",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Success",HttpStatus.OK);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, int numq) {
        List<Question> questions= questionRepository.findRandomQuestionsByCategory(category,numq);
        List<Integer>ids =new ArrayList<>(questions.stream().map(Question::getId).toList());
        return new ResponseEntity<>(ids,HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionDto>> getQuestionsFromId(List<Integer> questionId) {
        List<Question> question=new ArrayList<>();
        List<QuestionDto> questions=new ArrayList<>();
        int i=0;
        for(int id:questionId){
            question.add(questionRepository.findById(id).orElseThrow(()-> new RuntimeException("Question not found: "+id)));
            questions.add(QuestionDto.fromEntity(question.get(i)));
            i++;
        }
        return new ResponseEntity<>(questions,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {

        int right=0;
        for(Response r:responses){
            Question question=questionRepository.findById(r.getId()).get();
            if(r.getResponse().equals(question.getAnswer())){
                right++;
            }
        }
        return new ResponseEntity<>(right,HttpStatus.OK);}
}
