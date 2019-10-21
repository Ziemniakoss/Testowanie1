package com.testowanie.controllers;

import com.testowanie.MySqlInterface;
import com.testowanie.Task;
import com.testowanie.TaskList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CTasksDisplay implements Initializable {
	@FXML
	private ListView<TaskList> leftTaskListListView;
	@FXML
	private TextField leftNewListTextBox;
	@FXML
	private TextField centerNewTaskTextField;
	@FXML
	private ListView<Task> centerTaskListView;
	@FXML
	private CheckBox rightDoneCheckBox;
	@FXML
	private TextField rightTaskNameTextBox;
	@FXML
	private Button rightSaveChangesButton;

	private ObservableList<Task> tasks = FXCollections.observableArrayList();
	private ObservableList<TaskList> tasksLists = FXCollections.observableArrayList();
	private Task displayedTask;
	private TaskList displayedTaskList;
	private MySqlInterface db = new MySqlInterface();


	@FXML
	private void leftNewListTextBoxKeyReleased(KeyEvent keyEvent) {
		if (keyEvent.getCode() == KeyCode.ENTER) {
			addNewTaskList(leftNewListTextBox.getText().trim());
			leftNewListTextBox.setText("");
		}
		System.out.println(keyEvent.getCode());
	}

	@FXML
	private void newTaskListOnAction(ActionEvent actionEvent) {
		addNewTaskList(leftNewListTextBox.getText().trim());
		leftNewListTextBox.setText("");
	}

	private void addNewTaskList(String name) {
		TaskList newTaskList = new TaskList();
		newTaskList.setName(name);
		//todo dodawanie nowej listy zadan do bazy danych
		tasksLists.add(newTaskList);
		System.out.println("Dodano liste zadan: " + name);
	}

	/**
	 * Ładuje zadania, listy zadań z bazy danych i je wyświetla
	 */
	private void refresh() {
		//todo pobranie list zadan i jej wyswietlenie
		tasks.clear();
		rightDoneCheckBox.setSelected(false);
		rightTaskNameTextBox.setText("");
	}

	@FXML
	private void newTaskOnAction(ActionEvent actionEvent) {
		addNewTask(centerNewTaskTextField.getText().trim(), leftTaskListListView.getSelectionModel().getSelectedItem());
		centerNewTaskTextField.setText("");
	}

	private void addNewTask(String taskName, TaskList taskList) {
		Task newTask = new Task();
		newTask.setName(taskName);
		//todo Dodwanie nowego zadania do bazy danych
		tasks.add(newTask);
		System.out.println("Dodano zadanie: " + taskName + "do listy zadań " + taskList);
	}

	@FXML
	private void rightSaveChangesButtonOnAction(ActionEvent actionEvent) {
		//todo nadpisywanie zadania w bazie danych
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		leftTaskListListView.setItems(tasksLists);
		centerTaskListView.setItems(tasks);
		leftTaskListListView.setOnMouseClicked(e -> {
			TaskList selected = leftTaskListListView.getSelectionModel().getSelectedItem();
			if (selected != null && !selected.equals(displayedTaskList)) {
				displayedTaskList = selected;

				//todo zamiasit tworzenia tutaj listy t trezea wartosci pobrac z bazy
//				Lis

				centerTaskListView.getItems().clear();
//				centerTaskListView.getItems().addAll(result);
				System.out.println("Selected " + selected);
			}

		});
		centerTaskListView.getSelectionModel().selectedItemProperty().addListener((o, old, n) -> {
			//Wyświetl dane tego zadania
			if (n == null || n.equals(displayedTask))
				return;
			rightDoneCheckBox.setSelected(n.isInProgress());
			rightTaskNameTextBox.setText(n.getName());
		});

		refresh();
	}
}
