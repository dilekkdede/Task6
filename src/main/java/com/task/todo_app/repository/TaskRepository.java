package com.task.todo_app.repository;

import com.task.todo_app.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


    @Query(value = "select p from Task p where p.status =:status")
    List<Task> findTaskByStatus(int status);

}
