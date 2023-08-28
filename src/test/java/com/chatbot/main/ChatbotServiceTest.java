package com.chatbot.main;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.chatbot.main.entity.FAQ;
import com.chatbot.main.repository.FAQRepository;
import com.chatbot.main.services.FAQService;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ChatbotServiceTest {

    @Mock
    private FAQRepository repo;

    @InjectMocks
    private FAQService faqService;


    @Test
    public void testGetAnswerForValidQuestion() {
        String question = "Yes";
        String expectedAnswer = "Great the shift is at 1313 Mockingbird Ln at 2/15/2021 4:00pm-12:00am. We\'ll see you there!";
        when(repo.findByQuestion(question)).thenReturn(new FAQ(question, expectedAnswer));

        String result = faqService.getAnswerForQuestion(question);

        assertEquals(expectedAnswer, result);
    }

    @Test
    public void testAddFAQ() {
        String question = "Invalid";
        String answer = "Not Able to Perform";
        when(repo.findByQuestion(question)).thenReturn(null);

        String result = faqService.addFAQ(question, answer);

        assertEquals("FAQ Added Successfully", result);
    }

    @Test
    public void testUpdateFAQ() {
        Long id = 1L;
        String newAnswer = "Great the shift is at 1313 Mockingbird Ln at 2/15/2021 4:00pm-12:00am. We see you there!";
        FAQ faq = new FAQ("Question", "Answer");
        when(repo.findById(id)).thenReturn(java.util.Optional.of(faq));

        boolean result = faqService.updateFAQ(id, newAnswer);

        assertTrue(result);
        assertEquals(newAnswer, faq.getAnswer());
    }

    @Test
    public void testRemoveFAQ() {
        String question = "3";
        FAQ faq = new FAQ(question, "Ok. Thanks. I won\'t offer shifts at this location or time in the future!!");
        when(repo.findByQuestion(question)).thenReturn(faq);

        boolean result = faqService.removeFAQ(question);

        assertTrue(result);
    }

    @Test
    public void testGetAllQuestions() {
        List<String> expectedQuestions = new ArrayList<>(Arrays.asList("Yes", "No"));
        when(repo.findAllQuestion()).thenReturn(expectedQuestions);

        List<String> result = faqService.getAllQuestions();

        assertEquals(expectedQuestions, result);
    }


    @Test
    public void testGetAnswerForInvalidQuestion() {
        String question = "InvalidQuestion";
        when(repo.findByQuestion(question)).thenReturn(null);

        String result = faqService.getAnswerForQuestion(question);

        assertEquals("Not Found", result);
    }

    @Test
    public void testAddFAQWithNullInputs() {
        String question = null;
        String answer = null;

        String result = faqService.addFAQ(question, answer);

        assertEquals("FAQ addition failed!", result);
    }

    @Test
    public void testUpdateFAQWithInvalidId() {
    	Long id = 5L;
        String newAnswer = "Ok. Thanks. I won''t offer shift at this location or time in the future";
        
        when(repo.findById(id)).thenReturn(Optional.empty());

        boolean result = faqService.updateFAQ(id, newAnswer);

        assertFalse(result);
    }

    @Test
    public void testRemoveFAQWithNonexistentQuestion() {
        String question = "Hello";
        when(repo.findByQuestion(question)).thenReturn(null);

        boolean result = faqService.removeFAQ(question);

        assertFalse(result);
    }

    @Test
    public void testGetAllQuestionsWithNoQuestions() {
        when(repo.findAllQuestion()).thenReturn(new ArrayList<>());

        List<String> result = faqService.getAllQuestions();

        assertTrue(result.isEmpty());
    }
}
