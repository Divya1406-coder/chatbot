package com.chatbot.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.chatbot.main.entity.FAQ;

public interface FAQRepository extends JpaRepository<FAQ, Long> {
	FAQ findByQuestion(String question);

	@Query("select f.question from FAQ f")
	List<String> findAllQuestion();

}