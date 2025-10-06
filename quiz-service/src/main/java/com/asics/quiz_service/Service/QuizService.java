package com.asics.quiz_service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.asics.quiz_service.DAO.QuizDAO;
import com.asics.quiz_service.Model.Question;
import com.asics.quiz_service.Model.QuestionWrapper;
import com.asics.quiz_service.Model.Quiz;
import com.asics.quiz_service.Model.Response;
import com.asics.quiz_service.feign.QuizInterface;



@Service
public class QuizService {
	@Autowired
	QuizDAO quizDao;
	
	@Autowired
	QuizInterface quizInterface;

	public ResponseEntity<String> createQuiz(String category, String title) {
		
		List<Integer> questions = quizInterface.getQuestionsForQuiz(category).getBody();
		Quiz quiz = new Quiz();
		quiz.setTitle(title);
		quiz.setQuestionIds(questions);
		quizDao.save(quiz);
		
		
		return new ResponseEntity<>("sucess",HttpStatus.CREATED);
	
	}

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
		Quiz quiz =quizDao.findById(id).get();
		List<Integer> questionIds = quiz.getQuestionIds();
		
		ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionFromId(questionIds);
		
		
		return questions;
		
	}

	public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
		ResponseEntity<Integer> score = quizInterface.getScore(responses);
	
		return score;
	}
	
	
	
}
