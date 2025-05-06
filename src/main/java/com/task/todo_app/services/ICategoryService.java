package com.task.todo_app.services;

import com.task.todo_app.dto.dtoBase.BaseResponse;
import com.task.todo_app.dto.dtoRequest.CategoryDtoRequest;
import com.task.todo_app.dto.dtoResponse.TaskDtoResponse;

import java.util.List;

public interface ICategoryService {

    BaseResponse save(CategoryDtoRequest dto);

    BaseResponse findAll();

    BaseResponse findById(Long id);

    BaseResponse deleteById(Long id);

    BaseResponse updateCategory(Long id, CategoryDtoRequest dto);

    BaseResponse getTasksByCategoryId(Long id);
}
