package com.example.demo.dao;

import com.example.demo.model.Category;
import com.example.demo.pardao.CategoryParMapper;

public interface CategoryMapper extends CategoryParMapper {
    int deleteByPrimaryKey(Integer categoryId);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer categoryId);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
}