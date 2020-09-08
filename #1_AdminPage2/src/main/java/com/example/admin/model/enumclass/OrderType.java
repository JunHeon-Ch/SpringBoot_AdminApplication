package com.example.admin.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderType {

    ALL(0, "1", "1"),
    EACH(1, "2", "2");

    private Integer id;
    private String title;
    private String description;
}
