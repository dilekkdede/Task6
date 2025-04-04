package com.task.todo_app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Otomatik artan id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private int status;


    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToStringExclude
    private List<Task> tasks= new ArrayList<>();
}
