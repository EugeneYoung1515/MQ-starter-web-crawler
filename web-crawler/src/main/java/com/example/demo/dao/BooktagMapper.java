package com.example.demo.dao;

import com.example.demo.model.Booktag;
import com.example.demo.pardao.BooktagParMapper;

public interface BooktagMapper extends BooktagParMapper{
    int deleteByPrimaryKey(Integer booktagId);

    int insert(Booktag record);

    int insertSelective(Booktag record);

    Booktag selectByPrimaryKey(Integer booktagId);

    int updateByPrimaryKeySelective(Booktag record);

    int updateByPrimaryKey(Booktag record);
}