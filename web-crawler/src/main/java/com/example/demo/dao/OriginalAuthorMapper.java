package com.example.demo.dao;

import com.example.demo.model.OriginalAuthor;
import com.example.demo.pardao.OriginalAuthorParMapper;

public interface OriginalAuthorMapper extends OriginalAuthorParMapper{
    int deleteByPrimaryKey(Integer originalAuthorId);

    int insert(OriginalAuthor record);

    int insertSelective(OriginalAuthor record);

    OriginalAuthor selectByPrimaryKey(Integer originalAuthorId);

    int updateByPrimaryKeySelective(OriginalAuthor record);

    int updateByPrimaryKey(OriginalAuthor record);
}