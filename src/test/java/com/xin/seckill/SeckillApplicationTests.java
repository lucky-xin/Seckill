package com.xin.seckill;

import com.xin.seckill.dao.SeckillDao;
import com.xin.seckill.dto.ExecutionResultInfo;
import com.xin.seckill.dto.SeckillExposer;
import com.xin.seckill.pojo.Seckill;
import com.xin.seckill.service.SeckillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(SpringRunner.class)
//告诉junit spring的配置文件
//@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
@SpringBootTest(classes = SeckillApplication.class)
public class SeckillApplicationTests {

    @Test
    public void contextLoads() {
    }


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    //注入Service依赖
    @Autowired //@Resource
    private SeckillDao seckillDao;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> seckills = seckillService.getSeckillList();
        System.out.println(seckills);
    }

    @Test
    public void testExportSeckillUrl() {
        long id = 1000;
        SeckillExposer exposer = seckillService.exportSeckillUrl(id);
        System.out.println(exposer);
    }

    @Test
    public void getById() throws Exception {
        long seckillId = 1000;
        Seckill seckill = seckillService.getById(seckillId);
        System.out.println(seckill);
    }

    @Test
    public void getSeckillById() throws Exception {
        long seckillId = 1000;
        Seckill seckill = seckillDao.queryById(seckillId);
        System.out.println(seckill);
    }

    @Test//完整逻辑代码测试，注意可重复执行
    public void testSeckillLogic() throws Exception {
        long seckillId = 1000;
        SeckillExposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposed()) {

            System.out.println(exposer);
            long userPhone = 13476191876L;
            String md5 = exposer.getMd5();
            try {
                ExecutionResultInfo seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
                System.out.println(seckillExecution);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else {
            //秒杀未开启
            System.out.println(exposer);
        }
    }


    @Test//完整逻辑代码测试，注意可重复执行
    public void testExecuteSeckillProcedure() throws Exception {
        long seckillId = 1000;
        SeckillExposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposed()) {

            System.out.println(exposer);
            long userPhone = 13475591889L;
            String md5 = exposer.getMd5();
            try {
                ExecutionResultInfo seckillExecution = seckillService.executeSeckillProcedure(seckillId, userPhone, md5);
                System.out.println(seckillExecution);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else {
            //秒杀未开启
            System.out.println(exposer);
        }
    }
}
