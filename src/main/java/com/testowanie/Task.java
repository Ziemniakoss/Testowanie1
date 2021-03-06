package com.testowanie;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Task")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "taskId")
	private int taskId;
	@Column(name = "taskListId")
	private int taskListId;
	@Column(name = "taskName")
	private String taskName;
	@Column(name = "inProgress")
	@ColumnDefault("0")
	private boolean inProgress;

	public Task(){

	}

	public Task(String name, int taskListId){
		this.taskName = name;
		this.inProgress = false;
		this.taskListId = taskListId;
	}

	public int getId(){
		return taskId;
	}

	public int getTaskListId(){
		return taskListId;
	}

	public void setTaskListId(int taskListId) {
		this.taskListId = taskListId;
	}

	public String getName(){
		return taskName;
	}

	public void setName(String name){
		this.taskName = name;
	}

	public boolean isInProgress(){
		return inProgress;
	}

	public void setInProgress(boolean bool){
		this.inProgress = bool;
	}

	@Override
	public String toString() {
		return taskName;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Task task = (Task) o;
		return taskId == task.taskId;
	}

	@Override
	public int hashCode() {

		return Objects.hash(taskId);
	}
}