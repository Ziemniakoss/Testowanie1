package com.testowanie;

import java.util.Objects;

public class TaskList {
	private int id;
	private String name;

	public TaskList() {
	}

	public TaskList(int id,String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TaskList taskList = (TaskList) o;
		return id == taskList.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
