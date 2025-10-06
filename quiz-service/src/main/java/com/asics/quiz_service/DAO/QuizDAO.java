package com.asics.quiz_service.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asics.quiz_service.Model.Quiz;



public interface QuizDAO extends JpaRepository<Quiz, Integer> {

}
