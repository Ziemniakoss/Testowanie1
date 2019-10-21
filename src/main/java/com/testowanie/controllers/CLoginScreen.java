package com.testowanie.controllers;

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
		ViewManager.loadView("VTasksDisplay");
	}
}
