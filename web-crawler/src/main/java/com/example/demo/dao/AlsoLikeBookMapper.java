package com.example.demo.dao;

import com.example.demo.model.AlsoLikeBook;
import com.example.demo.pardao.AlsoLikeBookParMapper;

public interface AlsoLikeBookMapper extends AlsoLikeBookParMapper {
    int insert(AlsoLikeBook record);

    int insertSelective(AlsoLikeBook record);
}