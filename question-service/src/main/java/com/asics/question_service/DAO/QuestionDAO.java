package com.asics.question_service.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.asics.question_service.Model.Question;

@Repository
public interface QuestionDAO extends JpaRepository<Question,Integer> {

	List<Question> findByCategory(String category);
	
	@Query(value = "SELECT q.id FROM question q WHERE q.category=:category ORDER BY RAND() LIMIT 5",nativeQuery = true)
	List<Integer> findRandomQuestionsByCategory(String category);

}

