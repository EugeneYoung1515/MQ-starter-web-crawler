package org.ywcjxf.simple.redismq.core;

import java.util.ArrayList;
import java.util.List;

import static org.ywcjxf.simple.redismq.core.Constant.MQ_PREFIX;

public class KeysBuilder {
    private List<String> keys = new ArrayList<>(10);
    private String queueName;

    public KeysBuilder() {
    }

    public KeysBuilder(String queueName) {
        this.queueName = queueName;
    }

    public KeysBuilder add(String s){
        keys.add(s);
        return this;
    }

    public KeysBuilder queueName(String s){
        queueName = s;
        return this;
    }

    public List<String> build(){
        List<String> result = new ArrayList<>(10);
        for(String s:keys){
            result.add(MQ_PREFIX+queueName+s);
        }
        return result;
    }
}
