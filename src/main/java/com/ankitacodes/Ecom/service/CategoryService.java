package com.ankitacodes.Ecom.service;

import com.ankitacodes.Ecom.payloads.CategoryDto;
import com.ankitacodes.Ecom.payloads.PageResponse;

import java.util.List;

public interface CategoryService {

    //add category
    CategoryDto addCategory(CategoryDto categoryDto);

    //update Category
    CategoryDto updateCategory(CategoryDto categoryDto, Long catId);

    //delete Category
    void deleteCategory(Long catId);

    //get all Categories
    PageResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //get Category by catId
    CategoryDto getCategoryById(Long catId);
}
