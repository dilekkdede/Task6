package com.task.todo_app.dto.dtoResponse;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter

public class CategoryDtoResponse implements Serializable {


    private Long id;

    private String name;

    private int status;

    private String description;

    private Date createDate;

}
