package com.task.todo_app.enums;

import lombok.Getter;

@Getter
public enum RecordStatus {

    ACTIVE(1),
    DELETED(0),
    ARCHIVED(3),
    COMPLETED(2);

    int value;

    RecordStatus(int value) {
        this.value = value;
    }
}
