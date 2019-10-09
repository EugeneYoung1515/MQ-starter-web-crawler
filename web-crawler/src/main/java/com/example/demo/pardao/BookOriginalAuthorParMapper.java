package com.example.demo.pardao;

import com.example.demo.model.BookOriginalAuthor;

import java.util.List;

public interface BookOriginalAuthorParMapper {
    Integer selectCountByBookId(Integer bookId);
    Integer insertBatch(List<BookOriginalAuthor> list);
}
