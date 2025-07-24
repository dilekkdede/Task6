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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BaseResponse save(CategoryDtoRequest dto) {
        BaseResponse response = new BaseResponse();
        try {
            if (dto.getName() == null) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Kategori isim alanı boş geçilemez");
                return response;
            }

            Category category = modelMapper.map(dto, Category.class);
            category.setStatus(RecordStatus.ACTIVE.getValue());
            category.setCreateDate(new Date());

            Category dbCategory = categoryRepository.save(category);
            CategoryDtoResponse categoryDtoResponse = modelMapper.map(dbCategory, CategoryDtoResponse.class);

            response.setData(categoryDtoResponse);
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
            List<Category> categoryList = categoryRepository.findAllCategory();
            List<CategoryDtoResponse> responses = new ArrayList<>();

            for (Category category : categoryList) {
                CategoryDtoResponse dto = modelMapper.map(category, CategoryDtoResponse.class);
                responses.add(dto);
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
            Optional<Category> optional = categoryRepository.findById(id);
            if (optional.isEmpty()) {
                responseBase.setStatus(HttpStatus.NOT_FOUND.value());
                responseBase.setMessage("Girilen id ait kategori bulunamadı!");
                return responseBase;
            }

            CategoryDtoResponse response = modelMapper.map(optional.get(), CategoryDtoResponse.class);

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
            }

            categoryRepository.delete(optional.get());
            responseBase.setStatus(HttpStatus.OK.value());
            responseBase.setMessage("Kategori silindi:" + id);
            responseBase.setData(null);
            return responseBase;

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

            Optional<Category> optional = categoryRepository.findById(id);
            if (optional.isEmpty()) {
                responseBase.setStatus(HttpStatus.NOT_FOUND.value());
                responseBase.setMessage("Güncelleme yapılacak id bulunamadı");
                return responseBase;
            }

            Category dbCategory = optional.get();
            dbCategory.setName(dto.getName());
            dbCategory.setStatus(RecordStatus.ACTIVE.getValue());
            dbCategory.setDescription(dto.getDescription());
            Category updatedCategory = categoryRepository.save(dbCategory);
            CategoryDtoResponse response = modelMapper.map(updatedCategory, CategoryDtoResponse.class);

            responseBase.setData(response);
            responseBase.setStatus(HttpStatus.OK.value());
            responseBase.setMessage("Kategori Güncellendi:" + id);
            return responseBase;

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
            if (optional.isEmpty()) {
                categoryBaseResponse.setStatus(HttpStatus.NOT_FOUND.value());
                categoryBaseResponse.setMessage("Kategori bulunamadı!");
                return categoryBaseResponse;
            }

            List<Task> taskList = optional.get().getTasks();
            List<TaskDtoResponse> taskDtoList = new ArrayList<>();

            for (Task task : taskList) {
                TaskDtoResponse dto = modelMapper.map(task, TaskDtoResponse.class);
                taskDtoList.add(dto);
            }

            categoryBaseResponse.setData(taskDtoList);
            categoryBaseResponse.setStatus(HttpStatus.OK.value());
            categoryBaseResponse.setMessage("Girilen kategoriye ait görevler listesi:");

        } catch (Exception e) {
            categoryBaseResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            categoryBaseResponse.setMessage(e.getMessage());
        }

        return categoryBaseResponse;
    }
}
