package com.example.admin.model.network;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Pagination {

    private Integer totalPages;

    private Long totalElements;

    private Integer currentPage;

    private Integer currentElements;
}
