package com.task.todo_app.dto.dtoResponse;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter

public class CategoryDtoResponse implements Serializable {


    private Long id;

    private String name;

    private int status;

   // private TaskDtoResponse taskDto;
}
