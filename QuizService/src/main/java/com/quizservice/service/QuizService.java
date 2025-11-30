package com.quizservice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quizservice.model.Response;
import com.quizservice.feign.QuizInterface;
//import com.quizapp.model.Question;
//import com.quizapp.repository.QuestionRepository;
import com.quizservice.model.QuestionWrapper;
import com.quizservice.model.Quiz;
import com.quizservice.repository.QuizRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
	
	@Autowired
	QuizRepository quizRepository;
	
//	@Autowired
//	QuestionRepository questionRepository;
	
	@Autowired
	QuizInterface quizInterface;

	public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
		
		List<Integer> questions=quizInterface.getQuestionsForQuiz(category,numQ).getBody();
		
//		List<Question> questions=questionRepository.findRandomQuestionsByCategory(category,numQ);
		 Quiz quiz=new Quiz();
		 quiz.setTitle(title);
		 quiz.setQuestionIds(questions);
		 quizRepository.save(quiz);
		 return new ResponseEntity<>("Success",HttpStatus.CREATED);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
		Quiz quiz=quizRepository.findById(id).get();
		List<Integer> questionIds=quiz.getQuestionIds();
		quizInterface.getQuestionsFromId(questionIds);
		ResponseEntity<List<QuestionWrapper>> questions=quizInterface.getQuestionsFromId(questionIds);
		return questions;
	
		
	}

	public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
		ResponseEntity<Integer> score=quizInterface.getScore(responses);
		return score;
		
	}
	
	

}
