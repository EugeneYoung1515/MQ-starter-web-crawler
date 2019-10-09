package com.example.demo.pardao;

import com.example.demo.model.Series;

import java.util.List;

public interface SeriesParMapper {
    Series selectByName(String seriesName);
    Integer insertAndReturnId(Series series);
    List<Series> selectIn(List<String> list);
}
