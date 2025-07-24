package com.task.todo_app.repository;

import com.task.todo_app.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select a from Category a order by a.createDate desc ")
    List<Category> findAllCategory();

}
