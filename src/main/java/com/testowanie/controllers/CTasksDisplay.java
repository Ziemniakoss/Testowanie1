package com.testowanie.controllers;

import com.testowanie.MySqlInterface;
import com.testowanie.Task;
import com.testowanie.TaskList;
import com.testowanie.ViewManager;
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
import java.util.List;
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
	}

	@FXML
	private void newTaskListOnAction(ActionEvent actionEvent) {
		addNewTaskList(leftNewListTextBox.getText().trim());
		leftNewListTextBox.setText("");
	}

	private void addNewTaskList(String name) {
		if(name.compareTo("")!=0) {
			TaskList newTaskList = new TaskList();
			newTaskList.setName(name);
			db.addTaskList(name, ViewManager.getUser().getId());
			tasksLists.add(newTaskList);
			tasksLists.clear();
			tasksLists.addAll(db.getTaskLists(ViewManager.getUser()));
			System.out.println("Dodano liste zadan: " + name);
		}
	}

	/**
	 * Ładuje zadania, listy zadań z bazy danych i je wyświetla
	 */
	private void refresh() {
		tasksLists.addAll(db.getTaskLists(ViewManager.getUser()));
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
		if(taskName.compareTo("")!=0&&taskList!=null) {
			Task newTask = new Task();
			newTask.setName(taskName);
			db.addTask(taskName, displayedTaskList.getId());
			tasks.add(newTask);
			System.out.println("Dodano zadanie: " + taskName + "do listy zadań " + taskList);
			tasks.clear();
			tasks.addAll(db.getTasks(displayedTaskList));
		}
	}


	@FXML
	private void rightSaveChangesButtonOnAction(ActionEvent actionEvent) {
		if(displayedTask!=null) {
			if (displayedTask.getName().compareTo(rightTaskNameTextBox.getText().trim()) != 0 && rightTaskNameTextBox.getText().trim().compareTo("") != 0) {
				db.updateTaskName(displayedTask, rightTaskNameTextBox.getText().trim());
				displayedTask.setName(rightTaskNameTextBox.getText().trim());
				centerTaskListView.setItems(tasks);
				//task refresh
				tasks.clear();
				tasks.addAll(db.getTasks(displayedTaskList));
			}

			if (displayedTask.isInProgress() != rightDoneCheckBox.isSelected()) {
				db.updateTaskState(displayedTask, rightDoneCheckBox.isSelected());
				displayedTask.setInProgress(rightDoneCheckBox.isSelected());
			}
		}
	}


	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		leftTaskListListView.setItems(tasksLists);
		centerTaskListView.setItems(tasks);
		leftTaskListListView.setOnMouseClicked(e -> {
			TaskList selected = leftTaskListListView.getSelectionModel().getSelectedItem();
			if (selected != null && !selected.equals(displayedTaskList)) {
				displayedTaskList = selected;
				centerTaskListView.getItems().clear();
				centerTaskListView.getItems().addAll(db.getTasks(displayedTaskList));
				System.out.println("Selected " + selected);
			}

		});
		centerTaskListView.getSelectionModel().selectedItemProperty().addListener((o, old, n) -> {
			//Wyświetl dane tego zadania
			if (n == null || n.equals(displayedTask))
				return;
			displayedTask = n;
			rightDoneCheckBox.setSelected(n.isInProgress());
			rightTaskNameTextBox.setText(n.getName());
		});

		refresh();
	}

	public void centerNewTaskTextField(KeyEvent keyEvent) {
		if(keyEvent.getCode() ==KeyCode.ENTER){
			newTaskOnAction(null);
		}
	}

	@FXML
	private void displayedTaskNameKeyPressed(KeyEvent keyEvent) {
			rightSaveChangesButtonOnAction(null);

	}

	@FXML
	private void doneCheckBoxOnAction(ActionEvent actionEvent) {
		rightSaveChangesButtonOnAction(null);
	}
}
