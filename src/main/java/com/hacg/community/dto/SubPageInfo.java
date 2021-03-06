package com.hacg.community.dto;

import lombok.Data;

@Data
public class SubPageInfo {
    private Integer pageSize = 4;

    private Integer currentPage = 1;

    private Long total = 0l;
}
