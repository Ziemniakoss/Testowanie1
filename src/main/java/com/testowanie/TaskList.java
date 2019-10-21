package com.testowanie;

import javax.persistence.*;

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

}