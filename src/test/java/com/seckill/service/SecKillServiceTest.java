package com.seckill.service;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.SecKill;
import com.seckill.exception.SeckillException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 文件描述：秒杀业务集成测试类
 * 创建作者：陈苗
 * 创建时间：2016年5月28日 14:34
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-service.xml","classpath:spring/spring-dao.xml"})
public class SecKillServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SecKillService secKillService;

    @Test
    public void getSeckillList() throws Exception {
        List<SecKill> secKills = secKillService.getSeckillList();
        logger.info("结果={}",secKills);
    }

    @Test
    public void getSeckillById() throws Exception {
        long seckillId = 1000;
        SecKill secKill = secKillService.getSeckillById(seckillId);
        logger.info("秒杀商品为：" + secKill);
    }

    @Test
    public void testSeckillService() throws Exception {
        long seckillId = 1002;
        Exposer exposer = secKillService.exportSeckillUrl(seckillId);
        if(exposer.isExposed()) {
            long userPhone = 18706853053l;
            String md5 = exposer.getMd5();
            try {
                SeckillExecution seckillExecution = secKillService.executeSeckill(seckillId,userPhone,md5);
                logger.info("秒杀结果为：",seckillExecution.toString());
                System.out.println(seckillExecution);
            } catch (SeckillException e) {
                e.printStackTrace();
            }
        }
        else
            logger.warn("警告：" + exposer);
    }
    @Test
    public void testSeckillByProcedure() {
        long seckillId = 1001;
        long phone = 18706853013l;
        Exposer exposer = secKillService.exportSeckillUrl(seckillId);
        if(exposer.isExposed()) {
            String md5 = exposer.getMd5();
            SeckillExecution seckillExecution = secKillService.executeSeckillByProcedure(seckillId,phone,md5);
            System.out.println(seckillExecution.getStateInfo());
        }
    }
}