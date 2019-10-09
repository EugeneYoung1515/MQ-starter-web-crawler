package com.example.demo.handler;

import java.util.List;

public interface BatchHandler<T> {
    List<T> multi(List<String> name);
}
