package com.chatbot.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.chatbot.main.services.FAQService;

@SpringBootTest
@AutoConfigureMockMvc
public class ChatbotControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FAQService faqService;

	@Test
	public void testAskQuestion() throws Exception {
		String question = "Yes";

		mockMvc.perform(post("/ask").param("question", question)).andExpect(status().isOk()).andExpect(content().string(
				"Great the shift is at 1313 Mockingbird Ln at 2/15/2021 4:00pm-12:00am. We\'ll see you there!"));
	}
	
	@Test
    public void testAddFAQ() throws Exception {
        String question = "Not Possible";
        String answer = "Great the shift is at 1313 Mockingbird Ln at 2/15/2021 4:00pm-12:00am. We\\'ll see you there!";
        when(faqService.addFAQ(eq(question), eq(answer))).thenReturn("FAQ added successfully!");

        mockMvc.perform(post("/add")
                .param("question", question)
                .param("answer", answer))
                .andExpect(status().isOk())
                .andExpect(content().string("FAQ added successfully!"));
    }

	@Test
	public void testUpdateFAQ() throws Exception {
		Long id = 1L;
		String newAnswer = "Great the shift is at 1313 Mockingbird Ln at 2/15/2021 4:00pm-12:00am. We'll see you there!!!";

		when(faqService.updateFAQ(eq(id), anyString())).thenReturn(true);

		mockMvc.perform(MockMvcRequestBuilders.put("/{id}", id).content(newAnswer).contentType(MediaType.TEXT_PLAIN))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("FAQ updated successfully"));

		verify(faqService, times(1)).updateFAQ(eq(id), eq(newAnswer));
	}
	 
	
	@Test
    public void testAskQuestionWithInvalidInput() throws Exception {
        when(faqService.getAnswerForQuestion(anyString())).thenReturn(null);

        mockMvc.perform(get("/ask")
                .param("question", "InvalidQuestion"))
                .andExpect(content().string(""));
    }
	 @Test
	    public void testAddFAQWithInvalidInput() throws Exception {
	        when(faqService.addFAQ(anyString(), anyString())).thenReturn("FAQ addition failed!");

	        mockMvc.perform(post("/add")
	                .param("question", "")
	                .param("answer", ""))
	                .andExpect(content().string(""));
	    }

	 @Test
	    public void testUpdateFAQWithInvalidInput() throws Exception {
	        Long id = 1L;
	        String newAnswer = "NewValidAnswer";

	        // Simulate a failure when updating FAQ
	        when(faqService.updateFAQ(anyLong(), anyString())).thenReturn(false);

	        // Build the request
	        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/" + id)
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(newAnswer);

	        mockMvc.perform(request)
	                .andExpect(status().isNotFound()); // Adjust the expected status accordingly
	    }

    @Test
    public void testRemoveFAQWithInvalidInput() throws Exception {
        when(faqService.removeFAQ(anyString())).thenReturn(false);

        mockMvc.perform(post("/remove")
                .param("question", "InvalidQuestion"))
                .andExpect(status().isOk()) // Adjust the expected status accordingly
                .andExpect(content().string("FAQ removal failed!"));
    }

    @Test
    public void testGetAllQuestionsWithNoQuestions() throws Exception {
        when(faqService.getAllQuestions()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

}
