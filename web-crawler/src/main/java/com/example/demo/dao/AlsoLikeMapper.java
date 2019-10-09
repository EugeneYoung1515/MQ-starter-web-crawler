package com.example.demo.dao;

import com.example.demo.model.AlsoLike;
import com.example.demo.pardao.AlsoLikeParMapper;

public interface AlsoLikeMapper extends AlsoLikeParMapper{
    int deleteByPrimaryKey(Integer alsoLikeId);

    int insert(AlsoLike record);

    int insertSelective(AlsoLike record);

    AlsoLike selectByPrimaryKey(Integer alsoLikeId);

    int updateByPrimaryKeySelective(AlsoLike record);

    int updateByPrimaryKey(AlsoLike record);
}