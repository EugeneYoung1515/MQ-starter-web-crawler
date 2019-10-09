package com.example.demo.dao;

import com.example.demo.model.CategoryBook;

public interface CategoryBookMapper {
    int insert(CategoryBook record);

    int insertSelective(CategoryBook record);
}