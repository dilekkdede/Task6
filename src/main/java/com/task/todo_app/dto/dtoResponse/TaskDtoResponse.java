package com.task.todo_app.dto.dtoResponse;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class TaskDtoResponse implements Serializable {

    private Long id;

    private String title;

    private int status;

    private Date dueDate;



    private CategoryDtoResponse category;

}
