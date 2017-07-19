package com.seckill.dao;

import com.seckill.entity.SecKill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 文件描述：SecKillDao的单元测试类
 * NOTE:配置Spring和Junit整合，JUnit启动时加载Spring IOC容器
 * 创建作者：陈苗
 * 创建时间：2016年5月27日 23:33
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SecKillDaoTest {
    @Resource
    private SecKillDao secKillDao;
    @Test
    public void reduceNumber() throws Exception {
        int updateCount = secKillDao.reduceNumber(1000,new Date());
        System.out.println(updateCount);
    }

    @Test
    public void queryById() throws Exception {
        long id = 1000;
        SecKill secKill = secKillDao.queryById(id);
        System.out.println(secKill.getName());
        System.out.println(secKill);
    }

    @Test
    public void queryAll() throws Exception {
        List<SecKill> secKills = secKillDao.queryAll(0,100);
        for (SecKill secKill : secKills)
            System.out.println(secKill);
    }
}