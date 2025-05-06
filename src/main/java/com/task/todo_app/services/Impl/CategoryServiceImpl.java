package com.task.todo_app.services.Impl;

import com.task.todo_app.dto.dtoBase.BaseResponse;
import com.task.todo_app.dto.dtoRequest.CategoryDtoRequest;
import com.task.todo_app.dto.dtoResponse.CategoryDtoResponse;
import com.task.todo_app.dto.dtoResponse.TaskDtoResponse;
import com.task.todo_app.entity.Category;
import com.task.todo_app.entity.Task;
import com.task.todo_app.enums.RecordStatus;
import com.task.todo_app.repository.CategoryRepository;
import com.task.todo_app.services.ICategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public BaseResponse save(CategoryDtoRequest dto) {
        BaseResponse response = new BaseResponse();
        try {

            if (dto.getName() == null) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Kategori isim alanı boş geçilemez");
                return response;
            }

            CategoryDtoResponse categoryDtoResponse = new CategoryDtoResponse();
            Category category = new Category();
            BeanUtils.copyProperties(dto, category);
            category.setStatus(RecordStatus.ACTIVE.getValue());

            Category dbCategory = categoryRepository.save(category);
            BeanUtils.copyProperties(dbCategory, categoryDtoResponse);

            response.setData(dbCategory);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Kategori kayıt edildi.");
            return response;

        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return response;
        }
    }

    @Override
    public BaseResponse findAll() {
        BaseResponse categoryBaseResponse = new BaseResponse();
        try {


            List<CategoryDtoResponse> responses = new ArrayList<>();
            List<Category> categoryList = categoryRepository.findAll();
            for (Category category : categoryList) {
                CategoryDtoResponse categoryDtoResponse = new CategoryDtoResponse();
                BeanUtils.copyProperties(category, categoryDtoResponse);
                responses.add(categoryDtoResponse);
            }

            categoryBaseResponse.setData(responses);
            categoryBaseResponse.setStatus(HttpStatus.OK.value());
            categoryBaseResponse.setMessage("Kategori listesi");


        } catch (Exception e) {
            categoryBaseResponse.setMessage(e.getMessage());
            categoryBaseResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            categoryBaseResponse.setData(null);

        }
        return categoryBaseResponse;
    }

    @Override
    public BaseResponse findById(Long id) {
        BaseResponse responseBase = new BaseResponse();

        try {
            CategoryDtoResponse response = new CategoryDtoResponse();
            Optional<Category> optional = categoryRepository.findById(id);

            if (optional.isEmpty()) {
                responseBase.setStatus(HttpStatus.NOT_FOUND.value());
                responseBase.setMessage("Girilen id ait kategori bulunamadı!");
                return responseBase;
            } else {
                Category dbCategory = optional.get();
                BeanUtils.copyProperties(dbCategory, response);
            }
            responseBase.setData(response);
            responseBase.setStatus(HttpStatus.OK.value());
            responseBase.setMessage("Kategori id:" + id);
            return responseBase;

        } catch (Exception e) {
            responseBase.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseBase.setMessage(e.getMessage());
            return responseBase;
        }

    }

    @Override
    public BaseResponse deleteById(Long id) {
        BaseResponse responseBase = new BaseResponse();

        try {
            Optional<Category> optional = categoryRepository.findById(id);
            if (optional.isEmpty()) {
                responseBase.setStatus(HttpStatus.NOT_FOUND.value());
                responseBase.setMessage("Silinecek kategori id bulunamadı!");
                return responseBase;
            } else {
                categoryRepository.delete(optional.get());
                responseBase.setStatus(HttpStatus.OK.value());
                responseBase.setMessage("Kategori silindi:" + id);
                responseBase.setData(null);
                return responseBase;
            }
        } catch (Exception e) {
            responseBase.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseBase.setMessage(e.getMessage());
            return responseBase;
        }


    }

    @Override
    public BaseResponse updateCategory(Long id, CategoryDtoRequest dto) {
        BaseResponse responseBase = new BaseResponse();

        try {

            if (dto.getName() == null) {
                responseBase.setStatus(HttpStatus.BAD_REQUEST.value());
                responseBase.setMessage("Güncellenecek kategori isim alanı boş olamaz");
                return responseBase;
            }
            CategoryDtoResponse response = new CategoryDtoResponse();
            Optional<Category> optional = categoryRepository.findById(id);

            if (optional.isEmpty()) {
                responseBase.setStatus(HttpStatus.NOT_FOUND.value());
                responseBase.setMessage("Güncelleme yapılacak id bulunamadı");
                return responseBase;
            } else {
                Category dbCategory = optional.get();
                dbCategory.setName(dto.getName());
                dbCategory.setId(id);
                dbCategory.setStatus(dto.getStatus());

                Category updatedCategory = categoryRepository.save(dbCategory);
                BeanUtils.copyProperties(updatedCategory, response);

                responseBase.setData(response);
                responseBase.setStatus(HttpStatus.OK.value());
                responseBase.setMessage("Kategori Güncellendi:" + id);
                return responseBase;
            }
        } catch (Exception e) {
            responseBase.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseBase.setMessage(e.getMessage());
            return responseBase;
        }


    }

    @Override
    public BaseResponse getTasksByCategoryId(Long id) {
        BaseResponse categoryBaseResponse = new BaseResponse();
        try {
            Optional<Category> optional = categoryRepository.findById(id);
            List<TaskDtoResponse> taskDtoList = new ArrayList<>();

            if (optional.isPresent()) {
                Category category = optional.get();
                List<Task> taskList = category.getTasks();


                for (Task task : taskList) {
                    TaskDtoResponse taskDtoResponse = new TaskDtoResponse();
                    BeanUtils.copyProperties(task, taskDtoResponse);
                    taskDtoList.add(taskDtoResponse);
                }

                categoryBaseResponse.setData(taskDtoList);
                categoryBaseResponse.setStatus(HttpStatus.OK.value());
                categoryBaseResponse.setMessage("Girilen kategoriye ait görevler listesi:");
            } else {
                categoryBaseResponse.setStatus(HttpStatus.NOT_FOUND.value());
                categoryBaseResponse.setMessage("Kategori bulunamadı!");
            }

        } catch (Exception e) {
            categoryBaseResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            categoryBaseResponse.setMessage(e.getMessage());

        }

        return categoryBaseResponse;
    }

}
