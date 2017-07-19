package com.seckill.service;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.SecKill;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;

import java.util.List;

/**
 * 文件描述：秒杀库存信息的业务接口（站在使用者的角度设计接口）
 * NOTE:（1）方法定义粒度；（2）参数；（3）返回类型
 * 创建作者：陈苗
 * 创建时间：2016年5月28日 12:27
 */
public interface SecKillService {
    /**
     * 查询所有秒杀商品
     * @return
     */
    List<SecKill> getSeckillList();

    /**
     * 根据秒杀商品Id获取秒杀商品对象
     * @param seckillId
     * @return
     */
    SecKill getSeckillById(long seckillId);

    /**
     * 秒杀开启时输出秒杀接口地址，否则输出系统时间和秒杀时间
     * @param seckillId 秒杀商品Id
     * @return
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws RepeatKillException,SeckillCloseException,SeckillException;

    /**
     * 通过数据库端的存储过程达到高并发的执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws RepeatKillException
     * @throws SeckillCloseException
     * @throws SeckillException
     */
    SeckillExecution executeSeckillByProcedure(long seckillId, long userPhone, String md5);
}
