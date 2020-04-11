package com.github.heqiao2010.executor.queue_cached_executor.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 任务类型
 */
public enum TaskType {

    TYPE1("TYPE1"),

    TYPE2("TYPE2"),
    ;

    @Getter
    private String typeName;

    TaskType(String typeName) {
        this.typeName = typeName;
    }

    public static TaskType fromName(String typeName) {
        return Arrays.stream(values()).filter(type -> Objects.equals(type.getTypeName(), typeName))
                .findFirst().orElse(null);
    }
}
