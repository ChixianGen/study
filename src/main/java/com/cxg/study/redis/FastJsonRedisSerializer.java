package com.cxg.study.redis;   // Administrator 于 2019/8/19 创建;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;

import java.nio.charset.Charset;

public class FastJsonRedisSerializer implements RedisSerializer {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    static {
        // 可添加多个
        ParserConfig.getGlobalInstance().addAccept("com.threehealth.peripheral.");
//        ParserConfig.getGlobalInstance().addAccept("xxx.xxx.xxx.");
    }

    @Override
    public byte[] serialize(Object object) throws SerializationException {
        Assert.notNull(object, "系列化失败，缓存的对象不能为空");
        return JSON.toJSONString(object, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (null == bytes || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return JSON.parseObject(str, Object.class);
    }
}
