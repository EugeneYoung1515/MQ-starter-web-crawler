package com.example.demo.dao;

import com.example.demo.model.Series;
import com.example.demo.pardao.SeriesParMapper;

public interface SeriesMapper extends SeriesParMapper{
    int deleteByPrimaryKey(Integer seriesId);

    int insert(Series record);

    int insertSelective(Series record);

    Series selectByPrimaryKey(Integer seriesId);

    int updateByPrimaryKeySelective(Series record);

    int updateByPrimaryKey(Series record);
}