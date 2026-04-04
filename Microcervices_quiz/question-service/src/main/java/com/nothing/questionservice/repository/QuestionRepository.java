package com.nothing.questionservice.repository;

import com.nothing.questionservice.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByCategoryIgnoreCase(String category);

    @Query(
            value = "SELECT * FROM questions q WHERE q.category=:category ORDER BY RANDOM() LIMIT :numq"
            ,nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category, int numq);
}
