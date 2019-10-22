package com.testowanie;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest {

    private static Session session;
    private static MySqlInterface mySqlInnterface;
    private static SessionFactory factory;
    private static Transaction tx;
    private static List results;

    @BeforeClass
    public static void before() {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }

        mySqlInnterface = new MySqlInterface();
        Transaction tx = null;
        List results = null;
    }

    @Test
    public void addUserTest() {
        User user = new User("testUser", "pass");
        mySqlInnterface.addUser(user.getName(), user.getPassword());
        Session session = factory.openSession();

        try {
            tx = session.beginTransaction();
            String hql = "FROM User where name = :name";
            Query query = session.createQuery(hql);
            query.setParameter("name", user.getName());
            results = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        User userFromDB = (User) results.get(0);

        assertTrue(userFromDB.getName().compareTo(user.getName()) == 0);
    }

    @Test
    public void addUserSameNameTest() {
        User user = new User("testUser", "pass");
        User user2 = new User("testUser", "pass");
        mySqlInnterface.addUser(user.getName(), user.getPassword());
        mySqlInnterface.addUser(user2.getName(), user2.getPassword());
        Session session = factory.openSession();

        try {
            tx = session.beginTransaction();
            String hql = "FROM User where name = :name";
            Query query = session.createQuery(hql);
            query.setParameter("name", user.getName());
            results = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        assertTrue(results.size() == 1);
    }

    @Test
    public void addTaskListTest() {
        mySqlInnterface.addUser("ussername", "password");

        TaskList taskList = new TaskList("testTaskList", 1);
        mySqlInnterface.addTaskList(taskList.getName(), taskList.getUserId());
        Session session = factory.openSession();

        try {
            tx = session.beginTransaction();
            String hql = "FROM TaskList where taskListName = :name";
            Query query = session.createQuery(hql);
            query.setParameter("name", taskList.getName());
            results = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        TaskList taskListtFromDB = (TaskList) results.get(0);


        assertTrue(taskListtFromDB.getName().compareTo(taskList.getName()) == 0 && taskListtFromDB.getUserId() == taskList.getUserId());
    }

    @Test
    public void addTaskListNotExistUserTest() {
        TaskList taskList = new TaskList("testTaskList", -1);
        mySqlInnterface.addTaskList(taskList.getName(), taskList.getUserId());
        Session session = factory.openSession();

        try {
            tx = session.beginTransaction();
            String hql = "FROM TaskList where taskListName = :name";
            Query query = session.createQuery(hql);
            query.setParameter("name", taskList.getName());
            results = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        TaskList taskListtFromDB = (TaskList) results.get(0);


        assertFalse(taskListtFromDB.getName().compareTo(taskList.getName()) == 0 && taskListtFromDB.getUserId() == taskList.getUserId());
    }

    @Test
    public void addTaskTest() {
        mySqlInnterface.addUser("ussername", "password");
        mySqlInnterface.addTaskList("testTaskList", 1);
        TaskList taskList = mySqlInnterface.getTaskLists().get(0);

        Task task = new Task("testTask", taskList.getId());
        mySqlInnterface.addTask(task.getName(), task.getTaskListId());
        Session session = factory.openSession();

        try {
            tx = session.beginTransaction();
            String hql = "FROM Task where taskName = :name";
            Query query = session.createQuery(hql);
            query.setParameter("name", task.getName());
            results = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        Task taskFromDB = (Task) results.get(0);


        assertTrue(taskFromDB.getName().compareTo(task.getName()) == 0 && taskFromDB.getTaskListId() == task.getTaskListId());
    }

    @Test
    public void addTaskNotExistTaskListTest() {
        mySqlInnterface.addUser("ussername", "password");

        int numberOfTasksBefore = mySqlInnterface.getTasks().size();
        Task task = new Task("testTask", -1);
        mySqlInnterface.addTask(task.getName(), task.getTaskListId());
        Session session = factory.openSession();

        try {
            tx = session.beginTransaction();
            String hql = "FROM Task where taskName = :name";
            Query query = session.createQuery(hql);
            query.setParameter("name", task.getName());
            results = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        int numberOfTaskAfter = mySqlInnterface.getTasks().size();

        assertTrue(numberOfTaskAfter == numberOfTasksBefore);
    }


    @Test
    public void getUsersListTest() {
        mySqlInnterface.addUser("testUser", "pass");

        int numberOfUsersBefore = mySqlInnterface.getUsers().size();
        mySqlInnterface.addUser("newTestUser", "pass");
        int numberOfUsersAfter = mySqlInnterface.getUsers().size();

        assertTrue(numberOfUsersAfter == numberOfUsersBefore || numberOfUsersAfter == (numberOfUsersBefore + 1));
    }

    @Test
    public void getUserByNameTest() {
        mySqlInnterface.addUser("newTestUserByName", "pass");

        User user = mySqlInnterface.getUsers("newTestUserByName");

        assertTrue(user.getName().compareTo("newTestUserByName") == 0 && user.getPassword().compareTo("pass") == 0);
    }

    @Test
    public void getUserByNameNotExistTest() {

        User user = mySqlInnterface.getUsers("notExistUser");

        assertTrue(user == null);
    }

    @Test
    public void userExistTest() {

        mySqlInnterface.addUser("newTestUserByName", "pass");

        assertTrue(mySqlInnterface.userExists("newTestUserByName"));
    }

    @Test
    public void userNotExistTest() {
        assertFalse(mySqlInnterface.userExists("notExistUser"));
    }

    @Test
    public void getUsersTaskListTest() {
        mySqlInnterface.addUser("ussername", "password");
        mySqlInnterface.addTaskList("testTaskList", 1);
        User user = mySqlInnterface.getUsers().get(0);

        int numberOfTestListsBefore = mySqlInnterface.getTaskLists(user).size();
        mySqlInnterface.addTaskList("testTaskList2", 1);
        int numberOfTesltsListsAfter = mySqlInnterface.getTaskLists(user).size();

        assertTrue(numberOfTesltsListsAfter == numberOfTestListsBefore || numberOfTesltsListsAfter == (numberOfTestListsBefore + 1));
        assertFalse(mySqlInnterface.getTaskLists(user).isEmpty());

    }

    @Test
    public void getTaskListTest() {
        mySqlInnterface.addUser("ussername", "password");
        mySqlInnterface.addTaskList("testTaskList", 1);

        int numberOfTestListsBefore = mySqlInnterface.getTaskLists().size();
        mySqlInnterface.addTaskList("testTaskList2", 1);
        int numberOfTesltsListsAfter = mySqlInnterface.getTaskLists().size();

        assertTrue(numberOfTesltsListsAfter == numberOfTestListsBefore || numberOfTesltsListsAfter == (numberOfTestListsBefore + 1));
        assertFalse(mySqlInnterface.getTaskLists().isEmpty());
    }

    @Test
    public void getTasksTest() {
        mySqlInnterface.addUser("ussername", "password");
        mySqlInnterface.addTaskList("testTaskList", 1);
        mySqlInnterface.addTask("testTask", 1);

        TaskList taskList = mySqlInnterface.getTaskLists().get(0);

        int numberOfTaskBefore = mySqlInnterface.getTasks().size();
        mySqlInnterface.addTask("testTask2", taskList.getId());
        int numberOfTaskAfter = mySqlInnterface.getTasks().size();

        assertTrue(numberOfTaskAfter == numberOfTaskBefore || numberOfTaskAfter == (numberOfTaskBefore + 1));
        assertFalse(mySqlInnterface.getTasks().isEmpty());
    }

    @Test
    public void getTasksFromTaskListTest() {
        mySqlInnterface.addUser("ussername", "password");
        mySqlInnterface.addTaskList("testTaskList", 1);
        mySqlInnterface.addTask("testTask", 1);

        TaskList taskList = mySqlInnterface.getTaskLists().get(0);

        int numberOfTaskBefore = mySqlInnterface.getTasks(taskList).size();
        mySqlInnterface.addTask("testTask2", 1);
        int numberOfTaskAfter = mySqlInnterface.getTasks(taskList).size();

        assertTrue(numberOfTaskAfter == numberOfTaskBefore || numberOfTaskAfter == (numberOfTaskBefore + 1));
        assertFalse(mySqlInnterface.getTasks(taskList).isEmpty());
    }

    @Test
    public void updatePasswordTest() {
        mySqlInnterface.addUser("ussername", "password");
        User user = mySqlInnterface.getUsers("ussername");
        mySqlInnterface.updateUserPassword(user,"newPass");
        user = mySqlInnterface.getUsers("ussername");

        assertFalse("password".equals(user.getPassword()));
    }


    @Test
    public void updateUsernameTest() {

        User user = mySqlInnterface.getUsers("ussername");

        if(mySqlInnterface.userExists("newUsername"))
            mySqlInnterface.deleteUser(mySqlInnterface.getUsers("newUsername"));
        mySqlInnterface.updateUserName(user, "newUsername");

        assertTrue(mySqlInnterface.userExists("newUsername"));
        assertFalse(mySqlInnterface.userExists("ussername"));
    }

    @Test
    public void updateTaskListNameTest() {
        mySqlInnterface.addUser("ussername", "password");
        mySqlInnterface.addTaskList("testTaskList", 1);

        TaskList taskList = mySqlInnterface.getTaskLists().get(0);
        String nameBefore = taskList.getName();
        mySqlInnterface.updateTaskListName(taskList,"newTasklListName" + mySqlInnterface.getTaskLists().size());
        taskList = mySqlInnterface.getTaskLists().get(0);

        assertFalse(nameBefore.equals(taskList.getName()));
    }


    @Test
    public void updateTaskNameTest() {
        mySqlInnterface.addUser("ussername", "password");
        mySqlInnterface.addTaskList("testTaskList", 1);
        mySqlInnterface.addTask("testTask", 1);

        TaskList taskList = mySqlInnterface.getTaskLists().get(0);
        Task task = mySqlInnterface.getTasks(taskList).get(0);

        String nameBefore = task.getName();
        mySqlInnterface.updateTaskName(task,"newTaskName" + mySqlInnterface.getTasks().size());
        task = mySqlInnterface.getTasks(taskList).get(0);

        assertFalse(nameBefore.equals(task.getName()));
    }

    @Test
    public void updateTaskStateTest() {
        mySqlInnterface.addUser("ussername", "password");
        mySqlInnterface.addTaskList("testTaskList", 1);
        mySqlInnterface.addTask("testTask", 1);

        TaskList taskList = mySqlInnterface.getTaskLists().get(0);
        Task task = mySqlInnterface.getTasks(taskList).get(0);

        Boolean stateBefore = task.isInProgress();
        mySqlInnterface.updateTaskState(task, !task.isInProgress());
        task = mySqlInnterface.getTasks(taskList).get(0);

        assertFalse(stateBefore.equals(task.isInProgress()));
    }

    @Test
    public void deleteUserTest() {
        mySqlInnterface.addUser("userToDelete", "password");
        User user = mySqlInnterface.getUsers("userToDelete");
        mySqlInnterface.deleteUser(user);

        assertFalse(mySqlInnterface.userExists("userToDelete"));
    }

    @Test
    public void deleteTaskListTest() {
        mySqlInnterface.addUser("userToTestDeleteList", "password");
        User user = mySqlInnterface.getUsers("userToTestDeleteList");
        mySqlInnterface.addTaskList("taskListToDelete", user.getId());
        int numberOfTaskListsBefore = mySqlInnterface.getTaskLists(user).size();
        mySqlInnterface.deleteTaskList(mySqlInnterface.getTaskLists(user).get(0));
        int numberOfTaskListsAfter = mySqlInnterface.getTaskLists(user).size();

        assertFalse(numberOfTaskListsAfter == numberOfTaskListsBefore);
    }

    @Test
    public void deleteTaskTest() {
        mySqlInnterface.addUser("userToTestDeleteTask", "password");
        User user = mySqlInnterface.getUsers("userToTestDeleteTask");

        mySqlInnterface.addTaskList("listToTestTaskDelete", user.getId());
        TaskList taskList = mySqlInnterface.getTaskLists(user).get(0);
        mySqlInnterface.addTask("taskToDelete", taskList.getId());

        int numberOfTasksBefore = mySqlInnterface.getTasks(taskList).size();
        mySqlInnterface.deleteTask(mySqlInnterface.getTasks(taskList).get(0));
        int numberOfTasksAfter = mySqlInnterface.getTasks(taskList).size();

        assertFalse(numberOfTasksAfter == numberOfTasksBefore);
    }
}
