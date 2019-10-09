package com.example.demo.dao;

import com.example.demo.model.BookOriginalAuthor;
import com.example.demo.pardao.BookOriginalAuthorParMapper;

public interface BookOriginalAuthorMapper extends BookOriginalAuthorParMapper {
    int insert(BookOriginalAuthor record);

    int insertSelective(BookOriginalAuthor record);
}