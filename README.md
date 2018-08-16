# Seckill
# 秒杀系统
## 基于Redis实现分布式锁，给定一个key,所有秒杀请求先去Redis设置这个key的值,使用set添加NX（不存在则插入），PX(过期时间，避免异常没有释放锁)，只会有一个设置成功，设置成功就获得锁（就是秒杀成功），在获得锁期间秒杀商品数量减一，并把用户和商品信息插入数据库。操作结束之后删除该key。
# Redis分布式锁实现工具实现请看[Redis分布式锁](https://github.com/lucky-xin/Utils/blob/master/src/main/java/com/xin/utils/redis/RedisDistributedLock.java)  
## 秒杀实现,给定秒杀超时时间acquireTimeout，如果在acquireTimeout时间之内没有秒杀成功则直接失败退出，不然一直让while一直阻塞不太好，如果发出很多请求系统就崩溃了
[具体实现](https://github.com/luckyxin/Seckill/blob/master/src/main/java/com/xin/seckill/service/impl/SeckillServiceImpl.java)
```java
    public Seckill getOrPutSeckill(long seckillId, Function<Long, Seckill> getDataFromDb) {

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
```

