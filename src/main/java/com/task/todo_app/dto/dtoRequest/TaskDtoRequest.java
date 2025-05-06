package com.task.todo_app.dto.dtoRequest;

import com.task.todo_app.entity.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class TaskDtoRequest {


    private Long id;

    private String title;

    private int status;

    private Date dueDate;

    private Category category;
}
