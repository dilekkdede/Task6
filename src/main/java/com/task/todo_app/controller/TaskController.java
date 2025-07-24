package com.task.todo_app.controller;

import com.task.todo_app.dto.dtoBase.BaseResponse;
import com.task.todo_app.dto.dtoRequest.TaskDtoRequest;
import com.task.todo_app.services.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/rest/api/task")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class TaskController {

    @Autowired
    private ITaskService taskService;

    @PostMapping(path = "/save")
    public BaseResponse save(@RequestBody TaskDtoRequest dto) {
        return taskService.save(dto);
    }
    @GetMapping(path = "/find-all")
    public BaseResponse findAll(@RequestParam(name = "status", required = false) Integer status, @RequestParam(name = "categoryId", required = false) Long categoryId) {
        return taskService.findAll(status, categoryId);
    }
    @GetMapping(path = "/find-id/{id}")
    public BaseResponse findById(@PathVariable(name = "id") long id) {
        return taskService.findById(id);
    }

    @DeleteMapping(path = "/delete/{id}")
    public BaseResponse deleteById(@PathVariable(name = "id") long id) {
        return taskService.deleteById(id);
    }

    @PutMapping(path = "/update/{id}")
    public BaseResponse update(@PathVariable(name = "id") Long id, @RequestBody TaskDtoRequest dto) {
        return taskService.update(id, dto);
    }
    @GetMapping(path = "/completed/{id}")
    public BaseResponse completed(@PathVariable(name = "id") long id) {
        return taskService.completed(id);

    }
}
