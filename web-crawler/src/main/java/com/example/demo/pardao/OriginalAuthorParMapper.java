package com.example.demo.pardao;

import com.example.demo.model.OriginalAuthor;

import java.util.List;

public interface OriginalAuthorParMapper {
    OriginalAuthor selectByName(String authorName);
    Integer insertAndReturnId(OriginalAuthor originalAuthor);
    List<OriginalAuthor> selectIn(List<String> list);
    void insertBatchIgnoreIfExist(List<OriginalAuthor> list);
}
