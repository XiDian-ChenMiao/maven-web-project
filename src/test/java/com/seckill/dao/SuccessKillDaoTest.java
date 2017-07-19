package com.seckill.dao;

import com.seckill.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * 文件描述：秒杀成功明细表测试类
 * 创建作者：陈苗
 * 创建时间：2016年5月27日 0:21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKillDaoTest {
    @Resource
    private SuccessKillDao successKillDao;
    @Test
    public void insertSuccessKilled() throws Exception {
        long seckillId = 1000L;
        long userPhone = 18706853049L;
        int insertCount = successKillDao.insertSuccessKilled(seckillId,userPhone);
        System.out.println("插入结果：" + ((insertCount == 1) ? "成功" : "失败"));
    }

    @Test
    public void queryByIdWithSecKill() throws Exception {
        SuccessKilled successKilled = successKillDao.queryByIdWithSecKill(1000,18706853049L);
        System.out.println(successKilled);
        System.out.println(successKilled.getSecKill());
    }
}