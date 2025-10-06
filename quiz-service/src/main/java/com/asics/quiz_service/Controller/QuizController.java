package com.asics.quiz_service.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asics.quiz_service.Model.QuestionWrapper;
import com.asics.quiz_service.Model.QuizDTO;
import com.asics.quiz_service.Model.Response;
import com.asics.quiz_service.Service.QuizService;


@RestController
@RequestMapping("quiz")
public class QuizController {
	@Autowired
	QuizService quizService;
	
	@PostMapping("create")
	public ResponseEntity<String> createQuiz(@RequestBody QuizDTO quizDTO ){
		return quizService.createQuiz(quizDTO.getCategoryName(),quizDTO.getTitle());
	}
	
	@GetMapping("get/{id}")
	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
		return quizService.getQuizQuestions(id);
	}
	
	@PostMapping("submit/{id}")
	public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id,@RequestBody List<Response> responses){
		return quizService.calculateResult(id,responses);
	}
}
