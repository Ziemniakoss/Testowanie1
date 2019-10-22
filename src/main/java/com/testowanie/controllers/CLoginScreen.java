package com.testowanie.controllers;

import com.testowanie.MySqlInterface;
import com.testowanie.User;
import com.testowanie.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CLoginScreen {
	@FXML
	private TextField login;
	@FXML
	private PasswordField password;


	@FXML
	private void loginButton(ActionEvent actionEvent) {
		MySqlInterface db = new MySqlInterface();
		if (db.userExists(login.getText().trim())) {
			User u = db.getUsers(login.getText().trim());
			if (password.getText().equals(u.getPassword())) {
				ViewManager.setUser(u);
				ViewManager.loadView("VTasksDisplay");
			}
			else
				System.err.println("Wrong password");
		} else {
			User u = new User();
			u.setPassword(password.getText());
			u.setName(login.getText().trim());
			db.addUser(u.getName(),u.getPassword());
			u = db.getUsers(u.getName());
			ViewManager.setUser(u);
			ViewManager.loadView("VTasksDisplay");
		}
	}
}
