package org.ywcjxf.simple.redismq.core;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class SimpleRedisMQExecutor<T> {
    private SimpleRedisMQ simpleRedisMQ;
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private CallBack<T> callBack;

    private int consumerThread;

    private void pop(){
        for(int i=0;i<consumerThread;i++) {
            threadPoolTaskExecutor.execute(() -> {
                    while(true) {
                        try {
                            simpleRedisMQ.pop(callBack);
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
            }
            );
        }
    }

    public void push(T message){
        simpleRedisMQ.push(message);
    }

    public void execute(){
        pop();
    }

    public void setConsumerThread(int consumerThread) {
        this.consumerThread = consumerThread;
    }

    public void setCallBack(CallBack<T> callBack) {
        this.callBack = callBack;
    }

    public void setThreadPoolTaskExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    public void setSimpleRedisMQ(SimpleRedisMQ simpleRedisMQ) {
        this.simpleRedisMQ = simpleRedisMQ;
    }

    public SimpleRedisMQ getSimpleRedisMQ() {
        return simpleRedisMQ;
    }
}
