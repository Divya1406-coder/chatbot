package com.chatbot.main;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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
	public void testUpdateFAQ() throws Exception {
		Long id = 1L;
		String newAnswer = "Great the shift is at 1313 Mockingbird Ln at 2/15/2021 4:00pm-12:00am. We'll see you there!!!";

		when(faqService.updateFAQ(eq(id), anyString())).thenReturn(true);

		mockMvc.perform(MockMvcRequestBuilders.put("/{id}", id).content(newAnswer).contentType(MediaType.TEXT_PLAIN))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("FAQ updated successfully"));

		verify(faqService, times(1)).updateFAQ(eq(id), eq(newAnswer));
	}

}
