package com.example.demo.pardao;

import com.example.demo.model.TranslatorAuthor;

import java.util.List;

public interface TranslatorAuthorParMapper {
    TranslatorAuthor selectByName(String authorName);
    Integer insertAndReturnId(TranslatorAuthor translatorAuthor);
    List<TranslatorAuthor> selectIn(List<String> list);
    void insertBatchIgnoreIfExist(List<TranslatorAuthor> list);
}
