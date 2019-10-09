package org.ywcjxf.simple.redismq.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scripting.support.ResourceScriptSource;
import org.ywcjxf.simple.redismq.core.MessageWrapper;
import org.ywcjxf.simple.redismq.core.SimpleRedisMQ;
import org.ywcjxf.simple.redismq.core.SimpleRedisMQExecutor;
import org.ywcjxf.simple.redismq.spring.MapCacheDefaultScriptExecutor;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableScheduling
@EnableConfigurationProperties({SimpleRedisMQProperties.class,SimpleRedisProperties.class})
public class SimpleRedisMQAutoConfiguration {

    private SimpleRedisMQProperties simpleRedisMQProperties;
    private SimpleRedisProperties simpleRedisProperties;

    @Bean
    public SimpleRedisMQ simpleRedisMQ(RedisTemplate<String, MessageWrapper> stringMessageWrapperRedisTemplate){
        SimpleRedisMQ simpleRedisMQ = new SimpleRedisMQ();
        simpleRedisMQ.setMapper(objectMapper());
        simpleRedisMQ.setScript(script());
        simpleRedisMQ.setScript2(script2());
        simpleRedisMQ.setScript3(script3());
        simpleRedisMQ.setScript4(script4());

        simpleRedisMQ.setQueueName(simpleRedisMQProperties.getDefaultqueuename());
        simpleRedisMQ.setDelay(simpleRedisMQProperties.getDefaultdelay());
        simpleRedisMQ.setTimeout(simpleRedisMQProperties.getDefaulttimeout());

        simpleRedisMQ.setStringMessageWrapperRedisTemplate(stringMessageWrapperRedisTemplate);

        return simpleRedisMQ;
    }

    @Bean
    public <T>SimpleRedisMQExecutor<T> simpleRedisMQExecutor(SimpleRedisMQ simpleRedisMQ){
        SimpleRedisMQExecutor<T> simpleRedisMQExecutor = new SimpleRedisMQExecutor<>();
        simpleRedisMQExecutor.setSimpleRedisMQ(simpleRedisMQ);
        simpleRedisMQExecutor.setThreadPoolTaskExecutor(threadPoolTaskExecutor());

        simpleRedisMQExecutor.setConsumerThread(simpleRedisMQProperties.getConsumerthred());
        return simpleRedisMQExecutor;
    }

    @Bean(name = "script")
    public RedisScript<List> script() {

        DefaultRedisScript<List> luaScript = new DefaultRedisScript<List>();
        luaScript.setResultType(List.class);
        luaScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("luascript/executeLater.lua")));
        return luaScript;
    }

    @Bean(name = "script2")
    public RedisScript<List> script2() {

        DefaultRedisScript<List> luaScript = new DefaultRedisScript<List>();
        luaScript.setResultType(List.class);
        luaScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("luascript/toMain.lua")));
        return luaScript;
    }

    @Bean(name = "script3")
    public RedisScript<List> script3() {

        DefaultRedisScript<List> luaScript = new DefaultRedisScript<List>();
        luaScript.setResultType(List.class);
        luaScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("luascript/consume.lua")));
        return luaScript;
    }

    @Bean(name = "script4")
    public RedisScript<List> script4() {

        DefaultRedisScript<List> luaScript = new DefaultRedisScript<List>();
        luaScript.setResultType(List.class);
        luaScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("luascript/ack.lua")));
        return luaScript;
    }

    @Bean
    //public <T> RedisTemplate<String,T> redisTemplate(RedisConnectionFactory rcf){
    public  RedisTemplate<String,MessageWrapper> StringMessageWrapperRedisTemplate(RedisConnectionFactory rcf){
        //RedisTemplate<String, T> redisTemplate = new RedisTemplate<String, T>();
        RedisTemplate<String, MessageWrapper> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(rcf);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);//这几个常量的意思是
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS"));

        serializer.setObjectMapper(mapper);

        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);

        //可选
        redisTemplate.setScriptExecutor(new MapCacheDefaultScriptExecutor<String>(redisTemplate));

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    //这个还要不要条件注解 因为容器自带 有mvc依赖 容器才会自带这个
    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);//这几个常量的意思是
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS"));
        return mapper;
    }

    //这个还要不要条件注解 因为容器自带
    @Bean//("simpleRedisMQThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);
        executor.setMaxPoolSize(16);
        executor.setQueueCapacity(20);
        executor.setKeepAliveSeconds(100);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadFactory(new RedisPushPopFactory());//这里可能不好
        executor.initialize();
        return executor;
    }

    public static class RedisPushPopFactory implements ThreadFactory{
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(null,r,"pushPop",0);
            thread.setUncaughtExceptionHandler(new RedisPushPopUncaughtExceptionHandler());//这里可能不好
            return thread;
        }
    }

    public static class RedisPushPopUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println(t.getName()+" "+e);
        }
    }

    @Autowired
    public void setSimpleRedisMQProperties(SimpleRedisMQProperties simpleRedisMQProperties) {
        this.simpleRedisMQProperties = simpleRedisMQProperties;
    }

    @Autowired
    public void setSimpleRedisProperties(SimpleRedisProperties simpleRedisProperties) {
        this.simpleRedisProperties = simpleRedisProperties;
    }

}
