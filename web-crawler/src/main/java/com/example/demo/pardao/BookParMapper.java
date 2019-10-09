package com.example.demo.pardao;

import com.example.demo.model.Book;

import java.util.List;

public interface BookParMapper {
    Integer insertAndReturnId(Book book);
    List<Integer> selectAlsoLikeIdByBookTitle(List<String> list);
    Book selectByBookTitle(String bookTitle);
    List<Book> selectIn(List<String> list);
    Integer insertBatch(List<Book> list);
    void updateBatch(List<Book> list);
    void insertBatchIgnoreIfExist(List<Book> list);
}