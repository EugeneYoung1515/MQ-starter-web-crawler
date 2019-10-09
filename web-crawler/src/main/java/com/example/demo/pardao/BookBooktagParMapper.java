package com.example.demo.pardao;

import com.example.demo.model.BookBooktag;

import java.util.List;

public interface BookBooktagParMapper {
    Integer selectCountByBookId(Integer bookId);
    Integer insertBatch(List<BookBooktag> list);
}
