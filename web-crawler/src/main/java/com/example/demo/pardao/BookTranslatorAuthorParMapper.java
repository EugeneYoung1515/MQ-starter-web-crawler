package com.example.demo.pardao;

import com.example.demo.model.BookTranslatorAuthor;

import java.util.List;

public interface BookTranslatorAuthorParMapper {
    Integer selectCountByBookId(Integer bookId);
    Integer insertBatch(List<BookTranslatorAuthor> list);
}
