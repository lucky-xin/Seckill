package com.xin.seckill.dao;

import biz.datainsights.utils.StringUtil;
import biz.datainsights.utils.log.LogFactory;
import biz.datainsights.utils.redis.RedisClient;
import biz.datainsights.utils.redis.RedisDistributedLock;
import com.alibaba.fastjson.JSON;
import com.xin.seckill.pojo.Seckill;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: redis数据操作层
 * @date 2018-08-10 15:15
 * @Copyright (C)2018 , Luchaoxin
 */

public class RedisDao {

    private final Logger redisLog = LogFactory.getLogger("RedisDao");

    private String ip;

    private int port;

    private String password;

    public RedisDao(String ip, int port, String password) {
        this.ip = ip;
        this.password = password;
        this.port = port;
    }

    private Jedis getJedis() {
        return RedisClient.getJedis(ip, port, password);
    }

    /**
     * 从redis获取信息
     *
     * @param seckillId id
     * @return 如果不存在，则返回null
     */
    public Seckill getSeckill(long seckillId, Jedis jedis) {
        boolean isNewJedis = null == jedis;
        jedis = isNewJedis ? getJedis() : jedis;
        //redis操作逻辑
        try {
            String key = getSeckillRedisKey(seckillId);
            //并没有实现哪部序列化操作
            //采用自定义序列化
            //protostuff: pojo.
            byte[] bytes = jedis.get(key.getBytes());
            //缓存重获取到
            if (bytes != null) {

                String json = new String(bytes);
                Seckill seckill = JSON.parseObject(json,Seckill.class);
                return seckill;
            }
        } catch (Exception e) {
            redisLog.error("从redis获取信息异常异常", e);
        } finally {
            if (isNewJedis) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 从缓存获取，如果没有，则从数据库获取
     * 会用到分布式锁
     *
     * @param seckillId     id
     * @param getDataFromDb 从数据库获取的方法
     * @return 返回商品信息
     */
    public Seckill getSeckill(long seckillId, Function<Long, Seckill> getDataFromDb) {

        String lockKey = "seckill:locks:getSeckill:" + seckillId;
        String lockValue = null;
        Jedis jedis = getJedis();
        RedisDistributedLock redisDistributedLock = new RedisDistributedLock(jedis);
        try {
            // 循环直到获取到数据
            long acquireTimeout = 1000 * 5;
            long endTime = acquireTimeout + System.currentTimeMillis();
            do {
                Seckill seckill = getSeckill(seckillId, jedis);
                if (seckill != null) {
                    return seckill;
                }
                // 锁过期时间是防止程序突然崩溃来不及解锁，而造成其他线程不能获取锁的问题。过期时间是业务容忍最长时间。
                lockValue = redisDistributedLock.lock(lockKey, acquireTimeout, acquireTimeout, TimeUnit.MILLISECONDS);

                if (!StringUtil.isEmpty(lockValue)) {
                    // 获取到锁，从数据库拿数据, 然后存redis
                    seckill = getDataFromDb.apply(seckillId);
                    if (null == seckill) {
                        return null;
                    }
                    putSeckill(seckill, jedis);
                    return seckill;
                }
            } while (System.currentTimeMillis() < endTime);
        } catch (Exception e) {
            redisLog.error("获取Seckill异常", e);
        } finally {
            redisDistributedLock.unLock(lockKey, lockValue);
            jedis.close();
        }
        return null;
    }

    public String putSeckill(Seckill seckill) {
        return putSeckill(seckill, null);
    }

    public String putSeckill(Seckill seckill, Jedis jedis) {
        boolean isNewJedis = null == jedis;
        jedis = isNewJedis ? getJedis() : jedis;
        try {
            String key = getSeckillRedisKey(seckill.getSeckillId());

            String json = JSON.toJSONString(seckill);
            //超时缓存，1小时
            int timeout = 60;
            String result = jedis.setex(key.getBytes(), timeout, json.getBytes());
            return result;
        } catch (Exception e) {
            redisLog.error("Redis保存Seckill发生异常", e);
        } finally {
            if (isNewJedis) {
                jedis.close();
            }
        }
        return null;
    }


    /**
     * 根据id获取redis的key
     *
     * @param seckillId 商品id
     * @return redis的key
     */
    private String getSeckillRedisKey(long seckillId) {
        return "seckill:" + seckillId;
    }

}
