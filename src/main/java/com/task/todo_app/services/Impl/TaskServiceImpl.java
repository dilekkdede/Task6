package com.task.todo_app.services.Impl;

import com.task.todo_app.dto.dtoBase.BaseResponse;
import com.task.todo_app.dto.dtoRequest.TaskDtoRequest;
import com.task.todo_app.dto.dtoResponse.CategoryDtoResponse;
import com.task.todo_app.dto.dtoResponse.TaskDtoResponse;
import com.task.todo_app.entity.Category;
import com.task.todo_app.entity.Task;
import com.task.todo_app.enums.RecordStatus;
import com.task.todo_app.repository.CategoryRepository;
import com.task.todo_app.repository.TaskRepository;
import com.task.todo_app.services.ITaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements ITaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public BaseResponse save(TaskDtoRequest dto) {
        BaseResponse response = new BaseResponse();

        try {


            if (dto.getTitle() == null || dto.getTitle().isEmpty()) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Başlık kısmı boş bırakılamaz.");
                return response;
            }
            if (dto.getDueDate() == null) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Lütfen görevin bitiş tarihini giriniz! (YY-MM-DD)");
                return response;
            }
            if (dto.getCategory() == null) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Kategori bilgisi giriniz!");
                return response;
            }


            Long categoryId = dto.getCategory().getId();

            Optional<Category> optional = categoryRepository.findById(categoryId);
            if (optional.isEmpty()) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Girdiğiniz Id'ye ait kategori bulunmamaktadır!");
                return response;
            }


            TaskDtoResponse dtoResponse = new TaskDtoResponse();
            Task task = new Task();
            BeanUtils.copyProperties(dto, task);
            task.setStatus(RecordStatus.ACTIVE.getValue());
            task.setDueDate(new Date());
            Task dbTask = taskRepository.save(task);
            BeanUtils.copyProperties(dbTask, dtoResponse);

            response.setData(dtoResponse);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Task Başarılı bir şekilde kayıt edildi");
            return response;

        } catch (Exception e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage(e.getMessage());
            return response;
        }

    }

    @Override
    public List<BaseResponse> findAll(Integer status) {
        List<BaseResponse> list = new ArrayList<>();
        BaseResponse baseResponse = new BaseResponse();

        List<Task> tasks = new ArrayList<>();

        try {
            List<TaskDtoResponse> dtoResponseList = new ArrayList<>();
            if (status == null) {
                tasks = taskRepository.findAll();
            } else {
                tasks = taskRepository.findTaskByStatus(status);
            }


            for (Task task : tasks) {

                CategoryDtoResponse dtoResponseCategory = new CategoryDtoResponse();
                BeanUtils.copyProperties(task.getCategory(), dtoResponseCategory);
                TaskDtoResponse dtoResponse = new TaskDtoResponse();
                BeanUtils.copyProperties(task, dtoResponse);
                dtoResponse.setCategory(dtoResponseCategory);
                dtoResponseList.add(dtoResponse);
            }

            baseResponse.setData(dtoResponseList);
            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessage("Görevler listesi:");
            list.add(baseResponse);
            return list;

        } catch (Exception e) {
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            baseResponse.setData(null);
            list.add(baseResponse);
            return list;
        }


    }

    @Override
    public BaseResponse findById(long id) {

        BaseResponse response = new BaseResponse();

        try {
            TaskDtoResponse dtoResponse = new TaskDtoResponse();
            CategoryDtoResponse dtoCategory = new CategoryDtoResponse();
            Optional<Task> taskOptional = taskRepository.findById(id);

            if (taskOptional.isEmpty()) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Girilen Id ait Task bulunamadı");
                return response;
            } else {
                Task task = taskOptional.get();
                Category category = task.getCategory();
                BeanUtils.copyProperties(category, dtoCategory);
                BeanUtils.copyProperties(task, dtoResponse);
                dtoResponse.setCategory(dtoCategory);

            }

            response.setData(dtoResponse);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Girilen Id bilgileri:");
            return response;

        } catch (Exception e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage(e.getMessage());
            response.setData(null);
            return response;
        }


    }

    @Override
    public BaseResponse deleteById(long id) {
        BaseResponse response = new BaseResponse();

        try {
            Optional<Task> taskOptional = taskRepository.findById(id);

            if (taskOptional.isEmpty()) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Silincek Id Bulunamadı!");
                return response;
            } else {
                taskRepository.deleteById(id);
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Girilen Id başarılı olarak silindi");
                response.setData(null);
                return response;
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage(e.getMessage());
            response.setData(null);
            return response;
        }


    }

    @Override
    public BaseResponse update(Long id, TaskDtoRequest dto) {
        BaseResponse response = new BaseResponse();
        try {
            Optional<Category> categoryOptional = categoryRepository.findById(dto.getCategory().getId());
            if (categoryOptional.isEmpty()) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Girilen Id'ye ait kategori bulunmamaktadır!");
                return response;
            }


            TaskDtoResponse dtoResponse = new TaskDtoResponse();
            Optional<Task> taskOptional = taskRepository.findById(id);
            if (taskOptional.isPresent()) {
                Task task = taskOptional.get();
                task.setTitle(dto.getTitle());
                task.setCategory(dto.getCategory());
                task.setStatus(dto.getStatus());
                task.setDueDate(dto.getDueDate());

                Task dbTask = taskRepository.save(task);
                BeanUtils.copyProperties(dbTask, dtoResponse);

                response.setData(dtoResponse);
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Güncelleme başarılı");
                return response;
            }
            return null;
        } catch (Exception e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage(e.getMessage());
            response.setData(null);
            return response;
        }
    }

}
