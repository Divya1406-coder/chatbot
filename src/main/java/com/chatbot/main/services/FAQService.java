package com.chatbot.main.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatbot.main.entity.FAQ;
import com.chatbot.main.repository.FAQRepository;

@Service
public class FAQService {
	@Autowired
	public FAQRepository repo;

	public String getAnswerForQuestion(String question) {
		FAQ faq = repo.findByQuestion(question);
		if (faq != null) {
			return faq.getAnswer();
		} else {
			return "Not Found";
		}
	}

	public String addFAQ(String question, String answer) {
		if(question == null || answer == null) return "FAQ addition failed!";
		FAQ faq = repo.findByQuestion(question);
		if (faq == null) {
			faq = new FAQ();
			faq.setQuestion(question);
		}
		faq.setAnswer(answer);
		repo.save(faq);
		return "FAQ Added Successfully";
	}

	public boolean updateFAQ(Long id, String newAnswer) {
		FAQ faq = repo.findById(id).orElse(null);

		if (faq != null) {
			faq.setAnswer(newAnswer);
			repo.save(faq);
			return true;
		} else {
			return false;
		}
	}

	public boolean removeFAQ(String question) {
		FAQ faq = repo.findByQuestion(question);
		if (faq != null) {
			repo.delete(faq);
			return true;
		}
		return false;
	}

	public List<String> getAllQuestions() {
		List<String> faq = repo.findAllQuestion();
		if (faq != null) {
			return faq;
		} else {
			return new ArrayList<>();
		}

	}

}
