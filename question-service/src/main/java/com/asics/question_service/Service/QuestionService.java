package com.asics.question_service.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.asics.question_service.DAO.QuestionDAO;
import com.asics.question_service.Model.Question;
import com.asics.question_service.Model.QuestionWrapper;
import com.asics.question_service.Model.Response;

@Service
public class QuestionService {
	@Autowired
	QuestionDAO questionDao;
	public ResponseEntity<List<Question>> getAllQuestions(){
		try {
		return new ResponseEntity<>(questionDao.findAll(),HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
	}
	
	

	public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
		// TODO Auto-generated method stub
		try {
			return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
	}
	
	public ResponseEntity<String> addQuestion(Question question) {
		questionDao.save(question);
		return new ResponseEntity<>("success",HttpStatus.CREATED);
	}



	public ResponseEntity<List<Integer>> getQuestionForQuiz(String categoryName) {
		List<Integer> questions = questionDao.findRandomQuestionsByCategory(categoryName);
		return new ResponseEntity<>(questions,HttpStatus.OK);
	}



	public ResponseEntity<List<QuestionWrapper>> getQuestionFromId(List<Integer> questionIds) {
		List<QuestionWrapper> wrappers = new ArrayList<>();
		List<Question> questions = new ArrayList<>();
		
		for(Integer Id:questionIds) {
			questions.add(questionDao.findById(Id).get());
		}
		
		for(Question q:questions){
			QuestionWrapper wrapper = new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
			wrappers.add(wrapper);
		}
		
		return new ResponseEntity<>(wrappers,HttpStatus.OK);
	}



	public ResponseEntity<Integer> getScore(List<Response> responses) {
		int right=0;
		
		for(Response response:responses) {
			Question question = questionDao.findById(response.getId()).get();
			if(response.getResponse().equals(question.getRightAnswer()))
				right++;
		}
		
		return new ResponseEntity<>(right,HttpStatus.OK);
	}

}
