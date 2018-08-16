package com.xin.seckill.service.impl;

import com.google.common.collect.Maps;
import com.xin.seckill.dao.RedisDao;
import com.xin.seckill.dao.SeckillDao;
import com.xin.seckill.dao.SuccessKilledDao;
import com.xin.seckill.dto.ExecutionResultInfo;
import com.xin.seckill.dto.SeckillExposer;
import com.xin.seckill.enums.SeckillStatEnum;
import com.xin.seckill.exception.SeckillException;
import com.xin.seckill.pojo.Seckill;
import com.xin.seckill.pojo.SuccessKilled;
import com.xin.seckill.service.SeckillService;
import com.xin.utils.CollectionUtil;
import com.xin.utils.StringUtil;
import com.xin.utils.redis.RedisDistributedLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 服务层实现类
 * @date 2018-08-10 16:22
 * @Copyright (C)2018 , Luchaoxin
 */
@Service(value = "seckillService")
public class SeckillServiceImpl implements SeckillService {

    //日志对象
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //加入一个混淆字符串(秒杀接口)的salt，为了我避免用户猜出我们的md5值，值任意给，越复杂越好
    private final String salt = "shsdssljdd'l.";

    //注入Service依赖
    @Autowired //@Resource
    private SeckillDao seckillDao;

    @Autowired //@Resource
    private SuccessKilledDao successKilledDao;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    Jedis redisClient;

    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    @Override
    public Seckill getById(long seckillId) {
        return redisDao.getOrPutSeckill(seckillId, id -> seckillDao.queryById(id));
    }

    @Override
    public SeckillExposer exportSeckillUrl(long seckillId) {

        Seckill seckill = getById(seckillId);

        if (null == seckill) {
            return null;
        }

        //若是秒杀未开启
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        //系统当前时间
        Date nowTime = new Date();
        if (startTime.getTime() > nowTime.getTime() || endTime.getTime() < nowTime.getTime()) {
            return new SeckillExposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }

        //秒杀开启，返回秒杀商品的id、用给接口加密的md5
        String md5 = getMD5(seckillId);
        return new SeckillExposer(true, md5, seckillId);
    }

    private String getMD5(long seckillId) {
        String base = seckillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    //秒杀是否成功，成功:减库存，增加明细；失败:抛出异常，事务回滚
    @Override
    @Transactional
    /**
     * 使用注解控制事务方法的优点:
     * 1.开发团队达成一致约定，明确标注事务方法的编程风格
     * 2.保证事务方法的执行时间尽可能短，不要穿插其他网络操作RPC/HTTP请求或者剥离到事务方法外部
     * 3.不是所有的方法都需要事务，如只有一条修改操作、只读操作不要事务控制
     */
    public ExecutionResultInfo executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException {

        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            //秒杀数据被重写了
            return new ExecutionResultInfo(seckillId, SeckillStatEnum.DATE_REWRITE);
        }

        //执行秒杀逻辑:减库存+增加购买明细
        Date nowTime = new Date();
        try {
            //否则更新了库存，秒杀成功,增加明细
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            //看是否该明细被重复插入，即用户是否重复秒杀
            if (insertCount <= 0) {
                return new ExecutionResultInfo(seckillId, SeckillStatEnum.REPEAT_KILL);
            } else {
                //减库存,热点商品竞争
                int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
                if (updateCount <= 0) {
                    //没有更新库存记录，说明秒杀结束 rollback
                    return new ExecutionResultInfo(seckillId, SeckillStatEnum.END);
                } else {
                    tryRefreshRedis(seckillId);
                    //秒杀成功,得到成功插入的明细记录,并返回成功秒杀的信息 commit
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new ExecutionResultInfo(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //所以编译期异常转化为运行期异常
            throw new SeckillException("seckill inner error :" + e.getMessage());
        }
    }

    private void tryRefreshRedis(long seckillId) {
        RedisDistributedLock redisDistributedLock = new RedisDistributedLock(redisClient);
        String lockKey = "DistributedLock_" + seckillId;
        String lockValue = null;
        try {
            lockValue = redisDistributedLock.lock(lockKey, 5, 0, TimeUnit.SECONDS);
            boolean getLock = !StringUtil.isEmpty(lockValue);
            if (getLock) {
                long startTime = System.currentTimeMillis();
                long endTime = startTime + 3 * 1000;
                Seckill seckill = seckillDao.queryById(seckillId);
                do {
                    if ("OK".equals(redisDao.putSeckill(seckill))) {
                        break;
                    }
                } while (System.currentTimeMillis() < endTime);
            }
        } catch (InterruptedException e) {

        } finally {
            redisDistributedLock.unLock(lockKey, lockValue);
        }
    }

    @Override
    public ExecutionResultInfo executeSeckillProcedure(long seckillId, long userPhone, String md5) throws SeckillException {
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            //秒杀数据被重写了
            return new ExecutionResultInfo(seckillId, SeckillStatEnum.DATE_REWRITE);
        }
        Map<String, Object> params = Maps.newHashMap();
        params.put("seckillId", seckillId);
        params.put("phone", userPhone);
        params.put("killTime", new Date());
        params.put("result", null);

        try {
            successKilledDao.callSeckillProcedure(params);
            int result = CollectionUtil.getIntegerValue(params, "result", -2);
            if (result == 1) {
                //秒杀成功,得到成功插入的明细记录,并返回成功秒杀的信息 commit
                SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                tryRefreshRedis(seckillId);
                return new ExecutionResultInfo(seckillId, SeckillStatEnum.SUCCESS, successKilled);
            } else {
                return new ExecutionResultInfo(seckillId, SeckillStatEnum.stateOf(result));
            }
        } catch (Exception e) {
            return new ExecutionResultInfo(seckillId, SeckillStatEnum.INNER_ERROR);
        }
    }


}
