package com.testowanie.controllers;

import com.testowanie.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class CTasksDisplay {
	@FXML
	private TextField leftNewList;
	@FXML
	private CheckBox rightIsDone;
	@FXML
	private TextField rightTaskName;

	@FXML
	private void refresh(ActionEvent actionEvent) {
		ViewManager.loadView("VTasksDisplay");
	}

	@FXML
	private void newTaskListOnAction(ActionEvent actionEvent) {

	}
}
