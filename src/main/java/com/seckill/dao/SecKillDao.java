package com.seckill.dao;

import com.seckill.entity.SecKill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 文件描述：
 * 创建作者：陈苗
 * 创建时间：2016年月日 22:23
 */
public interface SecKillDao {
    /**
     * 减库存
     * @param seckillId 秒杀Id
     * @param killTime 秒杀时间
     * @return 如果影响行数大于1，表示更新的行数
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * 根据秒杀库存Id查询秒杀商品明细
     * @param seckillId
     * @return
     */
    SecKill queryById(long seckillId);

    /**
     * 根据偏移量查询秒杀物品列表
     * @param offet 偏移量
     * @param limit 数量
     * @return
     */
    List<SecKill> queryAll(@Param("offset") int offet, @Param("limit") int limit);

    /**
     * 使用存储过程执行秒杀
     * @param paramMap 参数映射
     */
    void killByProcedure(Map<String,Object> paramMap);
}
