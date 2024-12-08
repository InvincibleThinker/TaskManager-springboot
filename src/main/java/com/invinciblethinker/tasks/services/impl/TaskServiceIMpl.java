package com.invinciblethinker.tasks.services.impl;

import com.invinciblethinker.tasks.domain.entities.Task;
import com.invinciblethinker.tasks.domain.entities.TaskList;
import com.invinciblethinker.tasks.domain.entities.TaskPriority;
import com.invinciblethinker.tasks.domain.entities.TaskStatus;
import com.invinciblethinker.tasks.repositries.TaskListRepository;
import com.invinciblethinker.tasks.repositries.TaskRepository;
import com.invinciblethinker.tasks.services.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceIMpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    public TaskServiceIMpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<Task> listTasks(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }

    @Override
    public Task createTask(UUID taskListId, Task task) {
        if (null != task.getId()) {
            throw new IllegalArgumentException("Task already has an Id!");
        }

        if(null == task.getTitle() || task.getTitle().isBlank()){
            throw new IllegalArgumentException("Task must have a title!");
        }

        TaskPriority taskPriority = Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);

        TaskStatus taskStatus = TaskStatus.OPEN;

        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task List Id provider!"));

        LocalDateTime now = LocalDateTime.now();
        Task taskToSave = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskStatus,
                taskPriority,
                taskList,
                now,
                now
        );

        return taskRepository.save(taskToSave);

    }

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID taskId) {
        return taskRepository.findByTaskListIdAndId(taskListId, taskId);
    }

    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {
        if (null == task.getId()){
            throw new IllegalArgumentException("Task must have an Id!");
        }

        if (!Objects.equals(taskId, task.getId()))
        {
            throw new IllegalArgumentException("Task Id do not match");
        }

        if (null == task.getPriority()){
            throw new IllegalArgumentException("Task mush have valid Priority!");
        }

        if (null == task.getStatus()){
            throw new IllegalArgumentException("Task mush have valid status!");
        }

       Task existingTask = taskRepository.findByTaskListIdAndId(taskListId, taskId).orElseThrow(() ->
                new IllegalArgumentException("Task not found"));
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());;
        existingTask.setDueDate(task.getDueDate());
        existingTask.setPriority(task.getPriority());
        existingTask.setStatus(task.getStatus());
        existingTask.setUpdated(LocalDateTime.now());

        return  taskRepository.save(existingTask);

    }

    @Transactional
    @Override
    public void deleteTask(UUID taskListId, UUID taskId) {
        taskRepository.deleteByTaskListIdAndId(taskListId, taskId);
    }
}