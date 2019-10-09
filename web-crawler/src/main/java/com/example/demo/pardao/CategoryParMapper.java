package com.example.demo.pardao;

import com.example.demo.model.Category;

import java.util.List;

public interface CategoryParMapper {
    Category selectByName(String categoryName);
    Integer insertAndReturnId(Category category);
    List<Category> selectIn(List<String> list);
}
