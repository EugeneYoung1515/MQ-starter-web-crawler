package com.example.demo.dao;

import com.example.demo.model.PublishInfo;
import com.example.demo.pardao.PublishInfoParMapper;

public interface PublishInfoMapper extends PublishInfoParMapper{
    int deleteByPrimaryKey(Integer publishId);

    int insert(PublishInfo record);

    int insertSelective(PublishInfo record);

    PublishInfo selectByPrimaryKey(Integer publishId);

    int updateByPrimaryKeySelective(PublishInfo record);

    int updateByPrimaryKey(PublishInfo record);
}