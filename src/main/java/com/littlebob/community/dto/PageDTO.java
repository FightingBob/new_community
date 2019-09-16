package com.littlebob.community.dto;

import lombok.Data;

@Data
public class PageDTO {
    private Integer count;
    private Integer pageSize;
    private Integer lastPage;
    private Integer currentPage;
    private Integer nextPage;
    private Integer prePage;
    private Integer firstPage;
    private Integer endPage;
}
