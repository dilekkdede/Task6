package com.task.todo_app.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "task")
public class Task {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO) // Otomatik artan id
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "status")
    private int status;

    @Column(name = "due_date")
    private Date dueDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
