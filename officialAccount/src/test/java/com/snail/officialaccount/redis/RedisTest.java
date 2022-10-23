package com.snail.officialaccount.redis;

import com.snail.officialaccount.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @Author jiaSong.pi
 * @Date 2022/9/20 23:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RedisTest {

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void testAdd() {
        redisTemplate.opsForValue().set("key2", "我是新信息2", 1, TimeUnit.MINUTES);
        System.out.println(redisTemplate.opsForValue().get("key1"));

    }
}
