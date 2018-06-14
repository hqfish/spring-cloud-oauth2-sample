package com.yuhq.test;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest
{

    @Qualifier("stringRedisTemplate")
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Test
    public void test() throws Exception {

        System.out.println(stringRedisTemplate);
        System.out.println(connectionFactory);

        // 保存字符串
        stringRedisTemplate.opsForValue().set("aaa", "222");
        Assert.assertEquals("222", stringRedisTemplate.opsForValue().get("aaa"));

    }
}
