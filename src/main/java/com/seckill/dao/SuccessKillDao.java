package com.seckill.dao;

import com.seckill.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/**
 * 文件描述：秒杀成功DAO接口
 * 创建作者：陈苗
 * 创建时间：2016年5月28日 22:27
 */
public interface SuccessKillDao {
    /**
     * 插入购买明细，可过滤重复
     * @param seckillId 秒杀商品Id
     * @param userPhone 用户电话
     * @return 插入的结果行数
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * 根据Id查询SuccessKilled并携带秒杀商品的实体
     * @param seckillId 秒杀成功Id
     * @return
     */
    SuccessKilled queryByIdWithSecKill(@Param("seckillId") long seckillId,@Param("userPhone") long  userPhone);
}
