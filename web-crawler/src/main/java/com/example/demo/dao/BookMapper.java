package com.example.demo.dao;

import com.example.demo.model.Book;
import com.example.demo.pardao.BookParMapper;

public interface BookMapper extends BookParMapper{
    int deleteByPrimaryKey(Integer bookId);

    int insert(Book record);

    int insertSelective(Book record);

    Book selectByPrimaryKey(Integer bookId);

    int updateByPrimaryKeySelective(Book record);

    int updateByPrimaryKey(Book record);
}