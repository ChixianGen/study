package com.cxg.study.redis.lua;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * @Author: cxg
 * @Date: 2020/4/15 14:00
 * @Description: LUA脚本执行器配置文件 .
 * @Copyright: All rights reserved.
 */
@Deprecated
@Configuration
public class LuaConfiguration {

    @Bean("setEx-lua")
    public DefaultRedisScript<Boolean> setExScript() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/setEx.lua")));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }

    @Bean("limit-lua")
    public DefaultRedisScript<Boolean> limitScript() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/limit.lua")));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }
}
