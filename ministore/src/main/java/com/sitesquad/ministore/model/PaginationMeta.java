/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sitesquad.ministore.model;

import org.springframework.data.domain.Page;

/**
 *
 * @author ACER
 */
public class PaginationMeta {
    private Long totalCount;
    private Integer pageSize;
    private Integer totalPage;
    private Integer pageNumber;
    private Boolean isLast;
    private Boolean isFirst;

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Boolean getIsLast() {
        return isLast;
    }

    public void setIsLast(Boolean isLast) {
        this.isLast = isLast;
    }

    public Boolean getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(Boolean isFirst) {
        this.isFirst = isFirst;
    }
    
    public static <T> PaginationMeta createPagination(Page<T>  page){
        PaginationMeta  paginationMeta = new PaginationMeta();
        
        paginationMeta.setIsFirst(page.isFirst());
        paginationMeta.setIsLast(page.isLast());
        paginationMeta.setPageNumber(page.getNumber());
        paginationMeta.setPageSize(page.getSize());
        paginationMeta.setTotalCount(page.getTotalElements());
        paginationMeta.setTotalPage(page.getTotalPages());
        return paginationMeta;
    }
}
