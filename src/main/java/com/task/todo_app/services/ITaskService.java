package com.task.todo_app.services;

import com.task.todo_app.dto.dtoBase.BaseResponse;
import com.task.todo_app.dto.dtoRequest.TaskDtoRequest;

import java.util.List;

public interface ITaskService {

    BaseResponse save(TaskDtoRequest dto);

    List<BaseResponse> findAll();

    BaseResponse findById(long id);

    BaseResponse deleteById(long id);

    BaseResponse update(Long id, TaskDtoRequest dto);
}
