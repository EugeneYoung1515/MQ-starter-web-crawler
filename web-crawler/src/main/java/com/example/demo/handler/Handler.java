package com.example.demo.handler;

import java.util.List;

public interface Handler<T> {
    public Integer insertReturnIdOrFindReturnId(List<Integer> ids,String name);
    default T createObjectFromName(String name){return null;}
    default void insertBatchIgnoreIfExist(List<T> list){}
}
