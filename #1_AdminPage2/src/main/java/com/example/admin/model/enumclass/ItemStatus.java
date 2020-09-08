package com.example.admin.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ItemStatus {
    REGISTERED(0, "등록", "등록 상태"),
    UNREGISTERED(1, "a", "b");

    private Integer id;
    private String title;
    private String description;
}
