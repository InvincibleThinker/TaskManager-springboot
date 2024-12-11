package com.amar.tasks.mappers;

import com.amar.tasks.domain.dto.TaskDto;
import com.amar.tasks.domain.entities.Task;

public interface TaskMapper {

    Task fromDto(TaskDto taskDto);

    TaskDto toDto(Task task);

}
