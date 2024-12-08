package com.invinciblethinker.tasks.services;

import com.invinciblethinker.tasks.domain.entities.TaskList;

import java.util.List;

public interface TaskListService {
    List<TaskList> listTaskList();
    TaskList createTaskList(TaskList taskList);
}
