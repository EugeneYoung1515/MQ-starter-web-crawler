package com.example.demo.dao;

import com.example.demo.model.CategoryChildCategory;
import com.example.demo.pardao.CategoryChildCategoryParMapper;

public interface CategoryChildCategoryMapper extends CategoryChildCategoryParMapper{
    int insert(CategoryChildCategory record);

    int insertSelective(CategoryChildCategory record);
}