package com.cxg.study.redis.lua;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * @Author: cxg
 * @Date: 2020/4/16 10:05
 * @Description: 脚本工厂 .
 * @Copyright: All rights reserved.
 */
public final class LuaRedisScriptFactory {

    private final static String LUA_PATTERN = "lua/%s.lua";

    public static final <T> DefaultRedisScript<T> initScript(Class<T> clazz, String source) {
//        DefaultRedisScript<T> redisScript = new DefaultRedisScript<>();
        DefaultRedisScript redisScript = RedisScriptHolder.REDISSCRIPT;
        redisScript.setResultType(clazz);
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(String.format(LUA_PATTERN, source))));
        return redisScript;
    }

    private static final class RedisScriptHolder {
        private static DefaultRedisScript<?> REDISSCRIPT = new DefaultRedisScript<>();
    }
}
