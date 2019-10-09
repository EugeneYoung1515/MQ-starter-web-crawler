package org.ywcjxf.simple.redismq.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.ywcjxf.simple.redismq.core.Constant.*;


//写一个spring boot starter
//配置几个属性 默认队列名 延迟时间 过期时间 生产者线程数 消费者线程数

//spring 条件注解
//spring 获得资源的方法

//spring 调度 线程池

//不使用自动配置的话 要不要写一个工厂类

//要不要参考laravel 可以控制重试次数 以及控制消费者线程执行时间

public class SimpleRedisMQ {
    private RedisTemplate<String,MessageWrapper> stringMessageWrapperRedisTemplate;
    private ObjectMapper mapper;
    private RedisScript<List> script;
    private RedisScript<List> script2;
    private RedisScript<List> script3;
    private RedisScript<List> script4;

    private String queueName;

    private long delay;

    private long timeout;

    private static final KeysBuilder popKeysBuilder = new KeysBuilder().add(MAIN).add(RESERVERD).add(MESSAGE);
    private static final KeysBuilder delayToMainKeysBuilder = new KeysBuilder().add(DELAY).add(MAIN);
    private static final KeysBuilder ackKeysBuilder = new KeysBuilder().add(RESERVERD).add(MAIN).add(MESSAGE);
    private static final KeysBuilder reservedToMainKeysBuilder = new KeysBuilder().add(RESERVERD).add(MAIN);

    public SimpleRedisMQ() {
    }

    public SimpleRedisMQ(String queueName) {
        this.queueName = queueName;
    }

    //要不要用上泛型 SimpleRedisMQ MessageWrapper CallBack

    public <T> void push(T message){
        push(queueName,delay,message);
    }

    public <T> void push(long delay,T message){
        push(queueName,delay,message);
    }

    public <T> void push(String queueName,long delay,T message){
        executeLater(queueName,new MessageWrapper<T>(message,delay));
    }

    public <T> void pop(CallBack<T> callBack){
        pop(queueName,timeout,callBack);//这里设定了默认值1min
    }

    public <T> void pop(long timeout,CallBack<T> callBack){
        pop(queueName,timeout,callBack);
    }

    public <T> void pop(String queueName,long timeout,CallBack<T> callBack){
        delayToMain(queueName);
        reservedToMain(queueName);

        List<String> keys = popKeysBuilder.queueName(queueName).build();

        List result = stringMessageWrapperRedisTemplate.execute(script3,keys,System.currentTimeMillis()+timeout);

        if(!result.get(0).equals(NO_MESSAGE_SIGNAL)){//这里可能会有问题

            callBack.execute(((MessageWrapper<T>)result.get(0)).getMessage());

            ack(queueName, (Integer) result.get(1));//这里可能会有问题
            //ack(queueName, (Long) result.get(1));//这里可能会有问题
        }else{

            //结论不太对 就是没有wait 取不到时 不能wait 那就换成sleep
            /*
            try {
                Thread.sleep(100);//需要加这一行 时间长短要考虑 给jvm清理资源的时间
                //没这一行消费者线程空跑一段时间后 就好象卡组了 内存占用会升高

            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
            */
        }


    }

    //放到延时队列或者主队列
    private <T> void executeLater(String queueName,MessageWrapper<T> message){

        List<String> keys = new ArrayList<>(10);
        keys.add(MQ_PREFIX+queueName+ID);

        String messageJson = null;
        try{
            messageJson = mapper.writeValueAsString(message);
        }catch (JsonProcessingException ex){
            ex.printStackTrace();
        }

        keys.add(messageJson);
        keys.add(MQ_PREFIX+queueName+DELAY);
        keys.add(MQ_PREFIX+queueName+MAIN);
        keys.add(MQ_PREFIX+queueName+MESSAGE);

        List result = stringMessageWrapperRedisTemplate.execute(script,keys,System.currentTimeMillis()+message.getDelay());

    }

    //从延时队列放到主队列
    //这个方法还要放到哪里 一直可以循环进行

    @Scheduled(fixedRateString = "${simple.redismq.delaytomainscanrate}")
    public void delayToMain(){
        delayToMain(queueName);
    }

    public void delayToMain(String queueName){

        List<String> keys = delayToMainKeysBuilder.queueName(queueName).build();

        List result = stringMessageWrapperRedisTemplate.execute(script2,keys,System.currentTimeMillis());
    }

    //通知
    private void ack(String queueName,int id){//这里可能会有问题
    //private void ack(String queueName,Long id){//这里可能会有问题

        List<String> keys = ackKeysBuilder.queueName(queueName).build();

        List result = stringMessageWrapperRedisTemplate.execute(script4,keys,id);
    }

    //从备份队列放回主队列
    @Scheduled(fixedRateString = "${simple.redismq.reservedtomainscanrate}")
    public void reservedToMain(){
        reservedToMain(queueName);
    }

    public void reservedToMain(String queueName){

        List<String> keys = reservedToMainKeysBuilder.queueName(queueName).build();

        List result = stringMessageWrapperRedisTemplate.execute(script2,keys,System.currentTimeMillis());
    }

    public void setStringMessageWrapperRedisTemplate(RedisTemplate<String, MessageWrapper> stringMessageWrapperRedisTemplate) {
        this.stringMessageWrapperRedisTemplate = stringMessageWrapperRedisTemplate;
    }

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public void setScript(RedisScript<List> script) {
        this.script = script;
    }

    public void setScript2(RedisScript<List> script2) {
        this.script2 = script2;
    }

    public void setScript3(RedisScript<List> script3) {
        this.script3 = script3;
    }

     public void setScript4(RedisScript<List> script4) {
        this.script4 = script4;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
