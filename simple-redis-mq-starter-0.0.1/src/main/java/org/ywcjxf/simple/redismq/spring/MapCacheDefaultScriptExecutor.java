package org.ywcjxf.simple.redismq.spring;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultScriptExecutor;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapCacheDefaultScriptExecutor<K> extends DefaultScriptExecutor<K> {
    private RedisTemplate<K, ?> template;

    public MapCacheDefaultScriptExecutor(RedisTemplate<K, ?> template) {
        super(template);
        this.template = template;
        //System.out.println("new");
    }

    //private Map<RedisScript<?>,String> scriptStringMap = new HashMap<>(16);
    private ConcurrentHashMap<RedisScript<?>,String> scriptStringConcurrentHashMap = new ConcurrentHashMap<>(16);

    public <T> T execute(final RedisScript<T> script, final RedisSerializer<?> argsSerializer, final RedisSerializer<T> resultSerializer, final List<K> keys, final Object... args) {
        return this.template.execute(new RedisCallback<T>() {
            public T doInRedis(RedisConnection connection) throws DataAccessException {
                ReturnType returnType = ReturnType.fromJavaType(script.getResultType());
                byte[][] keysAndArgs = keysAndArgs(argsSerializer, keys, args);
                int keySize = keys != null ? keys.size() : 0;
                if (!connection.isPipelined() && !connection.isQueueing()) {
                    return eval(connection, script, returnType, keySize, keysAndArgs, resultSerializer);
                } else {
                    connection.eval(scriptBytes(script), returnType, keySize, keysAndArgs);
                    return null;
                }
            }
        });
    }

    /*
    @Override
    protected <T> T eval(RedisConnection connection, RedisScript<T> script, ReturnType returnType, int numKeys, byte[][] keysAndArgs, RedisSerializer<T> resultSerializer) {
        Object result;
        try {
            String sha1 = scriptStringMap.get(script);
            if(sha1!=null){
                result = connection.evalSha(sha1, returnType, numKeys, keysAndArgs);
            }else {
                result = connection.evalSha(script.getSha1(), returnType, numKeys, keysAndArgs);
                scriptStringMap.put(script,script.getSha1());
            }
        } catch (Exception var9) {
            if (!this.exceptionContainsNoScriptError(var9)) {
                throw var9 instanceof RuntimeException ? (RuntimeException)var9 : new RedisSystemException(var9.getMessage(), var9);
            }

            result = connection.eval(this.scriptBytes(script), returnType, numKeys, keysAndArgs);
            scriptStringMap.put(script,script.getSha1());
        }

        return script.getResultType() == null ? null : this.deserializeResult(resultSerializer, result);
    }
    */

    @Override
    protected <T> T eval(RedisConnection connection, RedisScript<T> script, ReturnType returnType, int numKeys, byte[][] keysAndArgs, RedisSerializer<T> resultSerializer) {
        Object result;
        try {
            /*//这个能用
            String sha1 = scriptStringMap.get(script);
            if(sha1==null){
                sha1 = script.getSha1();
                scriptStringMap.put(script,sha1);
            }
            */

            //result = connection.evalSha(sha1, returnType, numKeys, keysAndArgs);

            result = connection.evalSha(cache(script), returnType, numKeys, keysAndArgs);
        } catch (Exception var9) {
            if (!this.exceptionContainsNoScriptError(var9)) {
                throw var9 instanceof RuntimeException ? (RuntimeException)var9 : new RedisSystemException(var9.getMessage(), var9);
            }

            result = connection.eval(this.scriptBytes(script), returnType, numKeys, keysAndArgs);

            //scriptStringMap.put(script,script.getSha1());

            cache(script);
        }

        return script.getResultType() == null ? null : this.deserializeResult(resultSerializer, result);
    }

    private <T> String cache(RedisScript<T> script){
        String sha1 = scriptStringConcurrentHashMap.get(script);
        if(sha1==null){
            System.out.println("con");
            String newSha1 = script.getSha1();
            String preSha1 = scriptStringConcurrentHashMap.putIfAbsent(script,newSha1);
            if(preSha1==null){
                sha1 = newSha1;
            }else{
                sha1 = preSha1;
            }
        }
        return sha1;
    }

    private boolean exceptionContainsNoScriptError(Exception e) {
        if (!(e instanceof NonTransientDataAccessException)) {
            return false;
        } else {
            for(Object current = e; current != null; current = ((Throwable)current).getCause()) {
                String exMessage = ((Throwable)current).getMessage();
                if (exMessage != null && exMessage.contains("NOSCRIPT")) {
                    return true;
                }
            }

            return false;
        }
    }


}
