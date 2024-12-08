package com.invinciblethinker.tasks.mappers;

import com.invinciblethinker.tasks.domain.dto.TaskDto;
import com.invinciblethinker.tasks.domain.entities.Task;

public interface TaskMapper {

    Task fromDto(TaskDto taskDto);
    TaskDto toDto(Task task);
}
