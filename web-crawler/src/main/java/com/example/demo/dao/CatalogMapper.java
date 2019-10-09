package com.example.demo.dao;

import com.example.demo.model.Catalog;
import com.example.demo.pardao.CatalogParMapper;

public interface CatalogMapper extends CatalogParMapper{
    int deleteByPrimaryKey(Integer chapterId);

    int insert(Catalog record);

    int insertSelective(Catalog record);

    Catalog selectByPrimaryKey(Integer chapterId);

    int updateByPrimaryKeySelective(Catalog record);

    int updateByPrimaryKey(Catalog record);
}