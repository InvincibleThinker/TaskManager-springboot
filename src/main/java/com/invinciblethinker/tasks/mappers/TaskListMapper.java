package com.invinciblethinker.tasks.mappers;

import com.invinciblethinker.tasks.domain.dto.TaskListDto;
import com.invinciblethinker.tasks.domain.entities.TaskList;

public interface TaskListMapper {

    TaskList fromDto(TaskListDto taskListDto);

    TaskListDto toDto(TaskList taskList);
}
