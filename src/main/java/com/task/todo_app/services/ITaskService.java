package com.task.todo_app.services;

import com.task.todo_app.dto.dtoBase.BaseResponse;
import com.task.todo_app.dto.dtoRequest.TaskDtoRequest;

import java.util.List;

public interface ITaskService {

    BaseResponse save(TaskDtoRequest dto);

    BaseResponse findAll(Integer status, Long categoryId);

    BaseResponse findById(long id);

    BaseResponse deleteById(long id);

    BaseResponse update(Long id, TaskDtoRequest dto);

    BaseResponse completed(Long id);

}
