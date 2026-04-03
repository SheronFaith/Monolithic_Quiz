package com.nothing.monolithic_quiz.service;

import com.nothing.monolithic_quiz.dto.QuestionDto;
import com.nothing.monolithic_quiz.entity.Question;
import com.nothing.monolithic_quiz.entity.Quiz;
import com.nothing.monolithic_quiz.entity.Response;
import com.nothing.monolithic_quiz.repository.QuestionRepository;
import com.nothing.monolithic_quiz.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    public QuizRepository quizRepository;
    @Autowired
    private QuestionRepository questionRepository;

    public ResponseEntity<String> createQuiz(String category, int numq, String title) {

        List<Question> questions= questionRepository.findRandomQuestionsByCategory(category,numq);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizRepository.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionDto>> getQuizQuestions(int id) {
//        Optional<Quiz> quiz =quizRepository.findById(id);
//        List<Question> questionsFromDB = quiz.get().getQuestions();
//        List<QuestionDto> questionForUser=new ArrayList<>();
//        for(Question q: questionsFromDB){
//            QuestionDto qw=new QuestionDto(q.getId(),q.getQuestion(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
//            questionForUser.add(qw);
//
//        }

        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found: " + id));

        return ResponseEntity.ok(
                quiz.getQuestions().stream()
                        .map(QuestionDto::fromEntity)
                        .toList()
        );
    }

    public ResponseEntity<Integer> calculaateResult(int id, List<Response> response) {
        Quiz quiz= quizRepository.findById(id).orElseThrow(()-> new RuntimeException("Quiz not found: "+id));
        List<Question> questions=quiz.getQuestions();
        int right=0;
        int i=0;
        for(Response r:response){
            if(r.getResponse().equals(questions.get(i++).getAnswer())){
                right++;
            }
        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
