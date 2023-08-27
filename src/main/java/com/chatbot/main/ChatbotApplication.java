package com.chatbot.main;

import com.chatbot.main.ConsoleApp.*;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatbotApplication {
	
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		SpringApplication.run(ChatbotApplication.class, args);
		
		ChatbotApp chatapp = new ChatbotApp();
		chatapp.start();
	}

}
