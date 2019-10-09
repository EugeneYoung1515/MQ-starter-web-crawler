package com.example.demo.dao;

import com.example.demo.model.BookTranslatorAuthor;
import com.example.demo.pardao.BookTranslatorAuthorParMapper;

public interface BookTranslatorAuthorMapper extends BookTranslatorAuthorParMapper{
    int insert(BookTranslatorAuthor record);

    int insertSelective(BookTranslatorAuthor record);
}