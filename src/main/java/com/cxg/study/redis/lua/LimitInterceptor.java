package com.cxg.study.redis.lua;

/**
 * @Author: cxg
 * @Date: 2020/4/13 15:56
 * @Description: 限流切面实现 .
 * @Copyright: All rights reserved.
 */

import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Method;

@Slf4j
@Aspect
@Configuration
public class LimitInterceptor {
    private static final String UNKNOWN = "unknown";
//    private DefaultRedisScript<Boolean> limitScript;
    private RedisTemplate<String, Serializable> limitRedisTemplate;

//    @Autowired
//    @Qualifier("limit-lua")
//    public void setLimitScript(DefaultRedisScript<Boolean> limitScript) {
//        this.limitScript = limitScript;
//    }

    @Autowired
    @Qualifier("myRedisTemplate")
    public void setLimitRedisTemplate(RedisTemplate<String, Serializable> limitRedisTemplate) {
        this.limitRedisTemplate = limitRedisTemplate;
    }

    /**
     * @param pjp
     * @author fu
     * @description 切面
     * @date 2020/4/8 13:04
     */
    @Around("execution(public * *(..)) && @annotation(com.cxg.study.redis.lua.Limit)")
    public Object interceptor(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Limit limitAnnotation = method.getAnnotation(Limit.class);
        LimitType limitType = limitAnnotation.limitType();
        String name = limitAnnotation.name();
        String key;
        int limitPeriod = limitAnnotation.period();
        int limitCount = limitAnnotation.count();

        /**
         * 根据限流类型获取不同的key ,如果不传我们会以方法名作为key
         */
        switch (limitType) {
            case IP:
                key = getIpAddress();
                break;
            case CUSTOMER:
                key = limitAnnotation.key();
                break;
            default:
                key = StringUtils.upperCase(method.getName());
        }

        ImmutableList<String> keys = ImmutableList.of(StringUtils.join(limitAnnotation.prefix(), key));
        try {
//            String luaScript = buildLuaScript();
//            RedisScript<Number> redisScript = new DefaultRedisScript<>(luaScript);
            Boolean limit = limitRedisTemplate.execute(
                    LuaRedisScriptFactory.initScript(Boolean.class, "limit"), keys, limitPeriod, limitCount);
//            log.info("Access try count is {} for name = {} and key = {}", count, name, key);
            if (null != limit && limit.booleanValue()) {
                log.info("========== proceed ==========");
                return pjp.proceed();
            } else {
                throw new RuntimeException("加入黑名单");
            }
        } catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw new RuntimeException(e.getLocalizedMessage());
            }
            throw new RuntimeException("服务异常");
        }
    }

    /**
     * @author fu
     * @description 编写 redis Lua 限流脚本
     * @date 2020/4/8 13:24
     */
    public String buildLuaScript() {
        StringBuilder lua = new StringBuilder();
        lua.append("local c = redis.call('get',KEYS[1])");
        // 调用不超过最大值，则直接返回
        lua.append("\nif c and tonumber(c) > tonumber(ARGV[1]) then");
        lua.append("\nreturn c;");
        lua.append("\nend");
        // 执行计算器自加
        lua.append("\nc = redis.call('incr',KEYS[1])");
        lua.append("\nif tonumber(c) == 1 then");
        // 从第一次调用开始限流，设置对应键值的过期
        lua.append("\nredis.call('expire',KEYS[1],ARGV[2])");
        lua.append("\nend");
        lua.append("\nreturn c;");
        return lua.toString();
    }

    /**
     * @author fu
     * @description 获取id地址
     * @date 2020/4/8 13:24
     */
    public String getIpAddress() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
