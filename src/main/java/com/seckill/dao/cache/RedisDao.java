package com.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.seckill.entity.SecKill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 * 文件描述：使用Redis进行关于数据访问的后端缓存
 * 创建作者：陈苗
 * 创建时间：2016年5月29日 11:06
 */
public class RedisDao {
    private JedisPool jedisPool;
    private RuntimeSchema<SecKill> schema = RuntimeSchema.createFrom(SecKill.class);
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public RedisDao(String address,int port) {
        jedisPool = new JedisPool(address,port);
    }

    /**
     * 从Redis中获取秒杀对象
     * @param seckillId 秒杀对象的Id
     * @return 如果从其中获取到则返回找到的对象，否则返回null
     */
    public SecKill getSeckill(long seckillId) {
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckillId;
                //从Redis中获取到的是二进制数组数据，采用自定义序列化
                byte[] bytes = jedis.get(key.getBytes());
                if(bytes != null) {
                    //从缓存中获取到
                    SecKill secKill = schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes,secKill,schema);//被反序列化
                    return secKill;
                }
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * 向Redis中添加秒杀对象
     * @param secKill
     * @return 如果错误返回错误信息，如果正确返回OK
     */
    public String putSeckill(SecKill secKill) {
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + secKill.getSeckillId();
                byte[] bytes = ProtostuffIOUtil.toByteArray(secKill,schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                int timeout = 60 * 60;//一个小时，超时缓存
                String result = jedis.setex(key.getBytes(),timeout,bytes);
                return result;
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }
}
