package com.testowanie;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class MySqlInterface {

	private static SessionFactory factory;

	public MySqlInterface() {
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public Integer addUser(String name, String password) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer userID = null;

		MySqlInterface mySqlInterface = new MySqlInterface();

		List<User> users = mySqlInterface.getUsers();
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getName().compareTo(name) == 0) {
				return -1;
			}
		}

		try {
			tx = session.beginTransaction();
			User user = new User(name, password);
			userID = (Integer) session.save(user);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return userID;
	}

	public Integer addTaskList(String name, int userId) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer taskListId = null;

		try {
			tx = session.beginTransaction();
			TaskList taskList = new TaskList(name, userId);
			taskListId = (Integer) session.save(taskList);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return taskListId;
	}

	public Integer addTask(String name, int taskListId) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer taskId = null;

		try {
			tx = session.beginTransaction();
			Task task = new Task(name, taskListId);
			taskId = (Integer) session.save(task);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return taskId;
	}

	public List<User> getUsers() {
		Session session = factory.openSession();
		Transaction tx = null;
		List results = null;

		try {
			tx = session.beginTransaction();
			String hql = "FROM User";
			Query query = session.createQuery(hql);
			results = query.list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return results;
	}

	public User getUsers(String name) {
		Session session = factory.openSession();
		Transaction tx = null;
		List results = null;

		try {
			tx = session.beginTransaction();
			String hql = "FROM User where name=:name";
			Query query = session.createQuery(hql);
			query.setParameter("name",name);
			results = query.list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return results.size() > 0 ? (User) results.get(0) : null;
	}

	public boolean userExists(String name) {
		Session session = factory.openSession();
		Transaction tx = null;
		List results = null;

		try {
			tx = session.beginTransaction();
			String hql = "FROM User where name = :name";
			Query query = session.createQuery(hql);
			query.setParameter("name", name);
			results = query.list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return results.size() > 0;
	}

	public List<TaskList> getTaskLists() {
		Session session = factory.openSession();
		Transaction tx = null;
		List results = null;

		try {
			tx = session.beginTransaction();
			String hql = "FROM TaskList";
			Query query = session.createQuery(hql);
			results = query.list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return results;
	}

	public List<TaskList> getTaskLists(User user) {
		Session session = factory.openSession();
		Transaction tx = null;
		List results = null;

		try {
			tx = session.beginTransaction();
			String hql = "FROM TaskList T where T.userId = " + user.getId();
			Query query = session.createQuery(hql);
			results = query.list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return results;
	}

	public List<Task> getTasks() {
		Session session = factory.openSession();
		Transaction tx = null;
		List results = null;

		try {
			tx = session.beginTransaction();
			String hql = "FROM Task";
			Query query = session.createQuery(hql);
			results = query.list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return results;
	}

	public List<Task> getTasks(TaskList taskList) {
		Session session = factory.openSession();
		Transaction tx = null;
		List results = null;

		try {
			tx = session.beginTransaction();
			String hql = "FROM Task T where T.taskListId = " + taskList.getId();
			Query query = session.createQuery(hql);
			results = query.list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return results;
	}

	public void updateUserPassword(User user, String password) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			User userUpdated = (User) session.get(User.class, user.getId());
			userUpdated.setPassword(password);
			session.update(userUpdated);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public int updateUserName(User user, String name) {
		Session session = factory.openSession();
		Transaction tx = null;

		MySqlInterface mySqlInnterface = new MySqlInterface();

		List<User> users = mySqlInnterface.getUsers();
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getName().compareTo(name) == 0) {
				return -1;
			}
		}


		try {
			tx = session.beginTransaction();
			User userUpdated = (User) session.get(User.class, user.getId());
			userUpdated.setName(name);
			session.update(userUpdated);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return 0;
	}

	public void updateTaskListName(TaskList taskList, String name) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			TaskList taskListUpdated = (TaskList) session.get(TaskList.class, taskList.getId());
			taskListUpdated.setName(name);
			session.update(taskListUpdated);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void updateTaskName(Task task, String name) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Task taskUpdated = (Task) session.get(Task.class, task.getId());
			taskUpdated.setName(name);
			session.update(taskUpdated);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void updateTaskState(Task task, Boolean bool) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Task taskUpdated = (Task) session.get(Task.class, task.getId());
			taskUpdated.setInProgress(bool);
			session.update(taskUpdated);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void deleteUser(User user) {
		Session session = factory.openSession();
		Transaction tx = null;

		MySqlInterface mySqlInnterface = new MySqlInterface();
		List<TaskList> taskLists = mySqlInnterface.getTaskLists(user);
		for (int i = 0; i < taskLists.size(); i++) {
			mySqlInnterface.deleteTaskList(taskLists.get(i));
		}

		try {
			tx = session.beginTransaction();
			User userDelete = (User) session.get(User.class, user.getId());
			session.delete(userDelete);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void deleteTaskList(TaskList taskList) {
		Session session = factory.openSession();
		Transaction tx = null;

		MySqlInterface mySqlInnterface = new MySqlInterface();
		List<Task> tasks = mySqlInnterface.getTasks(taskList);
		for (int i = 0; i < tasks.size(); i++) {
			mySqlInnterface.deleteTask(tasks.get(i));
		}

		try {
			tx = session.beginTransaction();
			TaskList taskListDelete = (TaskList) session.get(TaskList.class, taskList.getId());
			session.delete(taskListDelete);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void deleteTask(Task task) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Task taskDelete = (Task) session.get(Task.class, task.getId());
			session.delete(taskDelete);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}
