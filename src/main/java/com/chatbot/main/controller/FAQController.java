package com.chatbot.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chatbot.main.services.FAQService;

@RestController
public class FAQController {
	
	public FAQController() {
		
	}
	@Autowired
	private FAQService faqService;

	@PostMapping("/ask")
	public String askQuestion(@RequestParam("question") String question) {
		return faqService.getAnswerForQuestion(question);
	}

	@PostMapping("/add")
	public String addFAQ(@RequestParam("question") String question, @RequestParam("answer") String answer) {
		String response = faqService.addFAQ(question, answer);
		return response;
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateFAQ(@PathVariable Long id, @RequestBody String newAnswer) {
		boolean updated = faqService.updateFAQ(id, newAnswer);

		if (updated) {
			return ResponseEntity.ok("FAQ updated successfully");
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/remove")
	public String removeFAQ(@RequestParam("question") String question) {
		boolean isdeleted = faqService.removeFAQ(question);
		return isdeleted ? "FAQ removed successfully!" : "FAQ removal failed!";
	}

	@GetMapping("/getAll")
	public List<String> getAllQuestions() {
		return faqService.getAllQuestions();
	}
}
