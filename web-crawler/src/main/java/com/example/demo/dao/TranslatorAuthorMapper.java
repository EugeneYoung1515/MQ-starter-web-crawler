package com.example.demo.dao;

import com.example.demo.model.TranslatorAuthor;
import com.example.demo.pardao.TranslatorAuthorParMapper;

public interface TranslatorAuthorMapper extends TranslatorAuthorParMapper{
    int deleteByPrimaryKey(Integer translatorAuthorId);

    int insert(TranslatorAuthor record);

    int insertSelective(TranslatorAuthor record);

    TranslatorAuthor selectByPrimaryKey(Integer translatorAuthorId);

    int updateByPrimaryKeySelective(TranslatorAuthor record);

    int updateByPrimaryKey(TranslatorAuthor record);
}