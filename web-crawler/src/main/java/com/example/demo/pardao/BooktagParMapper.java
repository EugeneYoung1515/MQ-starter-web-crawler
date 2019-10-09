package com.example.demo.pardao;

import com.example.demo.model.Booktag;

import java.util.List;

public interface BooktagParMapper {
    Booktag selectByName(String booktagName);
    Integer insertAndReturnId(Booktag booktag);
    List<Booktag> selectIn(List<String> list);
    void insertBatchIgnoreIfExist(List<Booktag> list);
}
