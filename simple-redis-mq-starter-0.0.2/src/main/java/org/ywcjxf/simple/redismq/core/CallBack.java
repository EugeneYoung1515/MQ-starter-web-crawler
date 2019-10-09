package org.ywcjxf.simple.redismq.core;
/*
public interface CallBack<T> {
    void execute(T t);
}
*/

import java.util.function.Consumer;

public interface CallBack<T> extends Consumer<T>{
    default void execute(T t){
        accept(t);
    }
}

