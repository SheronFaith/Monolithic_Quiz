package com.nothing.quizservice.service;

import com.nothing.quizservice.dto.QuestionDto;
import com.nothing.quizservice.entity.Quiz;
import com.nothing.quizservice.entity.Response;
import com.nothing.quizservice.feing.QuizInterface;
import com.nothing.quizservice.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    public QuizRepository quizRepository;

    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numq, String title) {

        List<Integer> questions= quizInterface.generateQuestions(category,numq).getBody();

         Quiz quiz = new Quiz();
         quiz.setTitle(title);
         quiz.setQuestionsIds(questions);
         quizRepository.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionDto>> getQuizQuestions(int id) {

        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found: " + id));
        List<Integer> questionsIds = quiz.getQuestionsIds();
        ResponseEntity<List<QuestionDto>> questions = quizInterface.getQuestionsFromId(questionsIds);

        return questions;

    }

    public ResponseEntity<Integer> calculaateResult(int id, List<Response> response) {
        return quizInterface.getScore(response);
    }
}
