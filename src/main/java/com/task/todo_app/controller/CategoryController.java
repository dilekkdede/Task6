package com.task.todo_app.controller;

import com.task.todo_app.dto.dtoBase.BaseResponse;
import com.task.todo_app.dto.dtoRequest.CategoryDtoRequest;
import com.task.todo_app.dto.dtoResponse.TaskDtoResponse;
import com.task.todo_app.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rest/api/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @PostMapping(path = "/save")
    public BaseResponse createCategory(@RequestBody CategoryDtoRequest dto) {
        return categoryService.save(dto);
    }

    @GetMapping(path = "find-all")
    public List<BaseResponse> findAll() {
        return categoryService.findAll();
    }

    @GetMapping(path = "/get-id/{id}")
    public BaseResponse findById(@PathVariable(name = "id") Long id) {
        return categoryService.findById(id);
    }

    @DeleteMapping(path = "/delete/{id}")
    public BaseResponse deleteCategoryById(@PathVariable(name = "id") Long id) {
        return categoryService.deleteById(id);
    }

    @PutMapping(path = "/update/{id}")
    public BaseResponse updateCategory(@PathVariable(name = "id") Long id, @RequestBody CategoryDtoRequest dto) {
        return categoryService.updateCategory(id, dto);
    }

    @GetMapping(path = "/get-tasks-by-category-id/{id}")
    public List<BaseResponse> getTasksByCategoryId(@PathVariable Long id) {
        return categoryService.getTasksByCategoryId(id);
    }
}
