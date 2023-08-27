package com.chatbot.main.ConsoleApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

public class ChatbotApp {

	List<String> ques;
	ConcurrentHashMap<String,String> queans = new ConcurrentHashMap<>();
	public ChatbotApp() {
		ques = new ArrayList<>();
	}

	public void start() throws InterruptedException, ExecutionException {
		Scanner sc = new Scanner(System.in);
		getAllQuestion();
		askQuestion();
		String question = "Hi! This is LISA. I have a great shift opportunity for you! Are you Interested in hearing about it?\r\n"
				+ "Please respond \"Yes\" or \"No\" ";
		String lastQuestion = question;

		while (true) {
			System.out.println(lastQuestion);

			String response = sc.nextLine();

			String answer = queans.getOrDefault(response,"Not Found");

			System.out.println(answer);

			if (!ques.contains(response)) {
				break;
			}

			if (answer.equals("Not Found")) {
				invalidHandler();
				lastQuestion = question;
				continue;
			}

			System.out.println(response);

			lastQuestion = answer;

		}
		 sc.close();
	}

	private void invalidHandler() {
		System.out.println("I'm sorry, I didn't understand your response.");
	}

	private void askQuestion() {
		String jdbcUrl = "jdbc:h2:mem:testdb";
		String username = "admin";
		String password = "admin";

		try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
			Statement statement = connection.createStatement();

			String query = "SELECT question,answer FROM FAQ";
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				String ques = resultSet.getString("question");
				String answer = resultSet.getString("answer");
				//System.out.println(ques + " " + answer);
				queans.put(ques,answer);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void getAllQuestion() {
		String jdbcUrl = "jdbc:h2:mem:testdb";
		String username = "admin";
		String password = "admin";

		try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
			Statement statement = connection.createStatement();

			String query = "SELECT question FROM FAQ";
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {

				String questions = resultSet.getString("question");
				if (!ques.contains(questions))
					ques.add(questions);
				//System.out.println(questions);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
