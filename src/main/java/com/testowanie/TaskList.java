package com.testowanie;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TaskList")
public class TaskList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "taskListId")
	private int taskListId;
	@Column(name = "taskListName")
	private String taskListName;
	@Column(name = "userId")
	private int userId;

	public TaskList(){
	}

	public TaskList(String name, int userId){
		this.taskListName = name;
		this.userId = userId;
	}

	public int getId(){
		return taskListId;
	}

	public String getName(){
		return taskListName;
	}

	public void setName(String name){
		this.taskListName = name;
	}

	public int getUserId(){
		return userId;
	}

	public void setUserId(int id){
		this.userId = id;
	}

	@Override
	public String toString() {
		return taskListName;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TaskList taskList = (TaskList) o;
		return taskListId == taskList.taskListId;
	}

	@Override
	public int hashCode() {

		return Objects.hash(taskListId);
	}
}