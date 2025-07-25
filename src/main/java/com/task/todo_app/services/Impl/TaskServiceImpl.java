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
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements ITaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @Autowired
    private ModelMapper modelMapper;

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
                response.setMessage("Lütfen görevin bitiş tarihini giriniz! (DD-MM-YYYY)");
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


            Task task = new Task();
            task.setTitle(dto.getTitle());
            task.setDueDate(dto.getDueDate());
            task.setCategory(categoryRepository.findById(categoryId).get());
            task.setStatus(RecordStatus.ACTIVE.getValue());


            Task savedTask = taskRepository.save(task);
            TaskDtoResponse dtoResponse = modelMapper.map(savedTask, TaskDtoResponse.class);


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
    public BaseResponse findAll(Integer status, Long categoryId) {
        BaseResponse baseResponse = new BaseResponse();

        try {
            List<Task> tasks = taskRepository.findTaskByStatus(status, categoryId);
            List<TaskDtoResponse> dtoResponseList = new ArrayList<>();

            for (Task task : tasks) {
                TaskDtoResponse dtoResponse = modelMapper.map(task, TaskDtoResponse.class);
                dtoResponseList.add(dtoResponse);
            }

            baseResponse.setData(dtoResponseList);
            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessage("Görevler listesi:");
        } catch (Exception e) {
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            baseResponse.setData(null);
        }

        return baseResponse;
    }

    @Override
    public BaseResponse findById(long id) {
        BaseResponse response = new BaseResponse();

        try {
            Optional<Task> taskOptional = taskRepository.findById(id);

            if (taskOptional.isEmpty()) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Girilen Id ait Task bulunamadı");
                return response;
            }

            TaskDtoResponse dtoResponse = modelMapper.map(taskOptional.get(), TaskDtoResponse.class);

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

            Optional<Task> taskOptional = taskRepository.findById(id);
            if (taskOptional.isPresent()) {
                Task task = taskOptional.get();
                task.setTitle(dto.getTitle());
                task.setCategory(dto.getCategory());
                task.setDueDate(dto.getDueDate());

                Task dbTask = taskRepository.save(task);
                TaskDtoResponse dtoResponse = modelMapper.map(dbTask, TaskDtoResponse.class);

                response.setData(dtoResponse);
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Güncelleme başarılı");
                return response;
            }

            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Task bulunamadı");
            return response;

        } catch (Exception e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage(e.getMessage());
            response.setData(null);
            return response;
        }
    }


    @Override
    public BaseResponse completed(Long id) {
        BaseResponse response = new BaseResponse();

        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setStatus(RecordStatus.COMPLETED.getValue());
            Task dbTask = taskRepository.save(task);

            TaskDtoResponse dtoResponse = modelMapper.map(dbTask, TaskDtoResponse.class);

            response.setData(dtoResponse);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Görev Başarıyla tamamlandı!");
        } else {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Tamamlanacak görev bulunamadı!");
            response.setData(null);
        }

        return response;
    }



}
