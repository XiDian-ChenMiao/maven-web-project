package com.seckill.service.impl;

import com.seckill.dao.SecKillDao;
import com.seckill.dao.SuccessKillDao;
import com.seckill.dao.cache.RedisDao;
import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.SecKill;
import com.seckill.entity.SuccessKilled;
import com.seckill.enums.SeckillStateEnum;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import com.seckill.service.SecKillService;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * 文件描述：秒杀业务实现类
 * 创建作者：陈苗
 * 创建时间：2016年5月28日 13:05
 */
@Service
public class SecKillServiceImpl implements SecKillService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SecKillDao secKillDao;
    @Autowired
    private SuccessKillDao successKillDao;
    @Autowired
    private RedisDao redisDao;

    private final String slat = "abcddefefdghichkkks";//添加盐值字符串，用于混淆MD5

    public List<SecKill> getSeckillList() {
        return secKillDao.queryAll(0, 4);
    }

    public SecKill getSeckillById(long seckillId) {
        return secKillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        //优化点：缓存优化（使用Redis）：超时的基础上维护一致性
        SecKill secKill = redisDao.getSeckill(seckillId);
        if(secKill == null) {
            secKill =secKillDao.queryById(seckillId);//从数据库中获取数据
            if (secKill == null)
                return new Exposer(false, seckillId);
            else
                redisDao.putSeckill(secKill);
        }
        Date startTime = secKill.getStartTime();
        Date endTime = secKill.getEndTime();
        Date currentTime = new Date();
        if (currentTime.getTime() < startTime.getTime() || currentTime.getTime() > endTime.getTime())
            return new Exposer(false, seckillId, currentTime.getTime(), startTime.getTime(), endTime.getTime());
        return new Exposer(true, getMD5(seckillId), seckillId);
    }

    /**
     * 获取加密字符串
     * @param seckillId
     * @return
     */
    private String getMD5(long seckillId) {
        String base = seckillId + "/" + slat;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
    @Transactional
    /**
     * 使用注解控制事务方法的优点：
     * （1）开发团队达成一致约定，明确标注事务方法的编程风格；
     * （2）保证事务方法的执行时间尽可能短，不要穿插其他网络操作；
     * （3）不是所有的方法都需要事务；
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws RepeatKillException, SeckillCloseException, SeckillException {
        if (md5 == null || !md5.equals(getMD5(seckillId)))
            throw new SeckillException("秒杀数据被重写");

        try {
            int insertCount = successKillDao.insertSuccessKilled(seckillId, userPhone);
            if (insertCount <= 0)
                throw new RepeatKillException("重复秒杀");
            else {//秒杀成功，减库存，热点商品竞争。两步操作：（1）减库存；（2）记录购买行为
                int updateCount = secKillDao.reduceNumber(seckillId, new Date());
                if (updateCount <= 0)
                    throw new SeckillCloseException("秒杀活动已经关闭");//事务进行回滚
                else {//事务进行提交
                    SuccessKilled successKilled = successKillDao.queryByIdWithSecKill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
                }
            }

        } catch (SeckillCloseException seckillCloseException) {
            throw seckillCloseException;
        } catch (RepeatKillException repeatKillException) {
            throw repeatKillException;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //将编译期的异常转化为运行期的异常
            throw new SeckillException("秒杀业务异常，异常信息：" + e.getMessage());
        }
    }

    /**
     * 通过存储过程执行秒杀业务
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    public SeckillExecution executeSeckillByProcedure(long seckillId, long userPhone, String md5) {
        if(md5 == null || !md5.equals(getMD5(seckillId)))
            return new SeckillExecution(seckillId,SeckillStateEnum.DATA_DEWRITE);
        Date killTime = new Date();
        Map<String,Object> paramMap = new Hashtable<String, Object>();
        paramMap.put("seckillId",seckillId);
        paramMap.put("phone",userPhone);
        paramMap.put("killTime",killTime);
        paramMap.put("result",new Integer(-2));//当存储过程执行完毕，result会被赋值
        try {
            secKillDao.killByProcedure(paramMap);
            int result = MapUtils.getInteger(paramMap,"result",-2);
            if(result == 1) {
                SuccessKilled sk = successKillDao.queryByIdWithSecKill(seckillId, userPhone);
                return new SeckillExecution(seckillId,SeckillStateEnum.SUCCESS,sk);
            } else {
                return new SeckillExecution(seckillId,SeckillStateEnum.stateOf(result));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new SeckillExecution(seckillId,SeckillStateEnum.INNER_ERROR);
        }
    }
}
