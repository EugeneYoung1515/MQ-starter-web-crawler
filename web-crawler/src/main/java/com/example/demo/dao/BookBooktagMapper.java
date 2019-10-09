package com.example.demo.dao;

import com.example.demo.model.BookBooktag;
import com.example.demo.pardao.BookBooktagParMapper;

public interface BookBooktagMapper extends BookBooktagParMapper{
    int insert(BookBooktag record);

    int insertSelective(BookBooktag record);
}