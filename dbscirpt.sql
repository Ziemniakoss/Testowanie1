create database todo;
use todo;
create table USER (
   id INT NOT NULL auto_increment,
   name VARCHAR(20) default NULL,
   password  VARCHAR(20) default NULL,
   PRIMARY KEY (id)
);

use todo;
create table TaskList (
   taskListId INT NOT NULL auto_increment,
   taskListName VARCHAR(20) default NULL,
   userId     INT,
   PRIMARY KEY (taskListId),
   FOREIGN KEY(userId) REFERENCES USER(id)
);

use todo;
create table Task (
   taskId INT NOT NULL auto_increment,
   taskName VARCHAR(100) default NULL,
   inProgress Boolean,
   taskListId     INT,
   PRIMARY KEY (taskId),
   FOREIGN KEY(taskListId) REFERENCES TaskList(taskListId)
);