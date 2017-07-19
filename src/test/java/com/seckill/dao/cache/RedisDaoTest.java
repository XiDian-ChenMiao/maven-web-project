package com.seckill.dao.cache;

import com.seckill.dao.SecKillDao;
import com.seckill.entity.SecKill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * 文件描述：Redis的存储测试类
 * 创建作者：陈苗
 * 创建时间：2016年5月29日 11:31
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {
    private long id = 1001;
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private SecKillDao secKillDao;

    /**
     * 测试秒杀相关RedisDao的存取方法
     * @throws Exception
     */
    @Test
    public void testSeckillRedisDao() throws Exception {
        SecKill secKill = redisDao.getSeckill(id);
        if(secKill == null) {
            secKill = secKillDao.queryById(id);
            if(secKill != null) {
                String result = redisDao.putSeckill(secKill);
                System.out.println(result);
                secKill = redisDao.getSeckill(id);
                System.out.println(secKill);
            } else {
                System.out.println("未能从数据库中获取到数据！");
            }
        }
    }
}