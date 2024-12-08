package com.invinciblethinker.tasks.controllers;

import com.invinciblethinker.tasks.domain.dto.TaskDto;
import com.invinciblethinker.tasks.domain.entities.Task;
import com.invinciblethinker.tasks.mappers.TaskMapper;
import com.invinciblethinker.tasks.services.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/task-lists/{task_list_id}/tasks")
public class TasksController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TasksController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public List<TaskDto> listTasks(@PathVariable("task_list_id")UUID taskListId){
        return taskService.listTasks(taskListId)
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @PostMapping
    public TaskDto createTask(
            @PathVariable("task_list_id")UUID taskListId,
            @RequestBody TaskDto taskdto){

        Task createdTask = taskService.createTask(
                taskListId,
                taskMapper.fromDto(taskdto)
        );

        return taskMapper.toDto(createdTask);
    }

    @GetMapping(path = "/{task_id}")
    public Optional<TaskDto> getTask(
            @PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID taskId
    ){
      return   taskService.getTask(taskListId, taskId).map(taskMapper::toDto);
    }

    @PutMapping(path = "/{task_id}")
    public TaskDto updatedTask(
            @PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID taskId,
            @RequestBody TaskDto taskDto
    ){
       Task updatedTask = taskService.updateTask(taskListId,taskId, taskMapper.fromDto(taskDto));
       return taskMapper.toDto(updatedTask);
    }

    @DeleteMapping(path = "/{task_id}")
    public  void deleteTask(
            @PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID taskId
    ){
        taskService.deleteTask(taskListId, taskId);
    }
}
