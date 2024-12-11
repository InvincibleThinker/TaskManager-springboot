package com.amar.tasks.mappers;

import com.amar.tasks.domain.dto.TaskListDto;
import com.amar.tasks.domain.entities.TaskList;

public interface TaskListMapper {

    TaskList fromDto(TaskListDto taskListDto);

    TaskListDto toDto(TaskList taskList);
}
