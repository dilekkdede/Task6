package com.task.todo_app.dto.dtoRequest;

import com.task.todo_app.dto.dtoResponse.TaskDtoResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter


public class CategoryDtoRequest {

    private Long id;

    private String name;

    private int status;

   // private TaskDtoResponse task;
}
