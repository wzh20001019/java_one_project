package com.xiaowen.test;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testString() {
        ValueOperations valueOperations = redisTemplate.opsForValue();

        valueOperations.set("key1", "zxcvbnm");

        // 设定 key, value  指定过期时间
        valueOperations.set("key2", "aszx", 10l, TimeUnit.SECONDS);

        // 只有在 key 不存在时才设置
        valueOperations.setIfAbsent("key5", "a21324");

        System.out.println(valueOperations.get("key1"));
    }

    @Test
    public void testHash() {
        HashOperations hashOperations = redisTemplate.opsForHash();

        hashOperations.put("001", "name", "wzh");
        hashOperations.put("001", "age", 20);

        Object name = hashOperations.get("001", "name");
        System.out.println(name);

        // 获取所有的 key
        Set keys = hashOperations.keys("001");

        // 获取所有的 value
        List values = hashOperations.values("001");
    }

    @Test
    public void testList() {
        ListOperations listOperations = redisTemplate.opsForList();

        listOperations.leftPush("list001", 1);
        listOperations.leftPushAll("list001", 5, 6, 7);

        // 获取所有值
        List values = listOperations.range("list001", 0, -1);

        // 获取列表长度
        Long len = listOperations.size("list001");
    }

    @Test
    public void testSet() {
        SetOperations setOperations = redisTemplate.opsForSet();

        setOperations.add("set001", 10, 1, "4");

        // 取值
        Set sets = setOperations.members("set001");

        setOperations.remove("set001", 10);
    }

    @Test
    public void testZSet() {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();

        zSetOperations.add("zset001", "name", 10);

        // 获取所有值
        zSetOperations.range("zset001", 0, -1);

        // 修改值
        zSetOperations.incrementScore("zset001", "name", 20);

        // 删除值
        zSetOperations.remove("zset001", "name");
    }

    @Test
    public void testCommon() {
        // 获取 Redis 中所有的key
        Set keys = redisTemplate.keys("*");

        // 判断 key 是否存在
        Boolean isKey = redisTemplate.hasKey("key");

        // 删除指定 key
        redisTemplate.delete("key");

        // 获取指定key对应value的数据类型
        DataType dataType = redisTemplate.type("key");
        System.out.println(dataType.name());

    }
}
