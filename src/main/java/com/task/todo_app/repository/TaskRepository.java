package com.task.todo_app.repository;

import com.task.todo_app.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


    @Query(value = "select p from Task p where (p.status =:status or :status is null) and(p.category.id =:categoryId or :categoryId is null) order by p.id desc")
    List<Task> findTaskByStatus(Integer status, Long categoryId);


}
