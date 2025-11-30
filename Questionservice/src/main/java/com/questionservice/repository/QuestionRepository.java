package com.questionservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.questionservice.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer>{

	List<Question> findByCategory(String category);

	@Query(value = "SELECT q.id FROM question q WHERE q.category = ?1 ORDER BY RAND() LIMIT ?2",
		       nativeQuery = true)
	List<Integer> findRandomQuestionsByCategory(String category, int numQ);

	
}
