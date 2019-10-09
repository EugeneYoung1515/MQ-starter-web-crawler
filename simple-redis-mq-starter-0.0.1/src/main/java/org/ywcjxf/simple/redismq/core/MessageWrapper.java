package org.ywcjxf.simple.redismq.core;

public class MessageWrapper<T> {
    private T message;
    private String id;
    private long delay;

    public MessageWrapper() {
    }

    public MessageWrapper(T message) {
        this.message = message;
    }

    public MessageWrapper(T message, long delay) {
        this.message = message;
        this.delay = delay;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

}
