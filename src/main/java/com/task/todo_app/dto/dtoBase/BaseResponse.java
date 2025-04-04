package com.task.todo_app.dto.dtoBase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse implements Serializable {

    private Object data;
    private int status;
    private String message;
}
