package com.example.demo.pardao;

import com.example.demo.model.Catalog;

import java.util.List;

public interface CatalogParMapper {
    Integer insertBatch(List<Catalog> list);
    Integer selectCountByBookId(Integer bookId);
}
