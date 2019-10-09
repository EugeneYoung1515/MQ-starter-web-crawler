package com.example.demo.pardao;

import com.example.demo.model.AlsoLikeBook;

import java.util.List;

public interface AlsoLikeBookParMapper {
    Integer insertBatch(List<AlsoLikeBook> list);
}
