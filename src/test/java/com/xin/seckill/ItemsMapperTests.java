package com.xin.seckill;

import com.github.pagehelper.PageHelper;
import com.xin.seckill.dao.ItemsDao;
import com.xin.seckill.pojo.Items;
import com.xin.utils.StringUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 测试
 * @date 2018-08-12 10:36
 * @Copyright (C)2018 , Luchaoxin
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeckillApplication.class)
public class ItemsMapperTests {

    @Autowired
    private SqlSession sqlSession;

    @Autowired
    private PageHelper pageHelper;

    @Autowired
    private ItemsDao itemsDao;

    @Test
    public void insertTest() {
        int count = 10;
        Items items = null;
        for (int i = 0; i < count; i++) {
            String name = StringUtil.getRandomString(5);
            items = new Items(name, 5 + i, 10000, "挖矿机", "https://kclks", new Date());
            int result = itemsDao.insert(items);
        }

    }

    @Test
    public void deleteByPrimaryKeyTest() {
        itemsDao.deleteByPrimaryKey(14);
    }

    @Test
    public void updateByPrimaryKeyTest() {
        String name = StringUtil.getRandomString(5);
        Items items = new Items("更新名称", 13, 2, "挖矿机", "https://kclks", new Date());
        itemsDao.updateByPrimaryKey(items);
    }

    @Test
    public void updateByItemsTest() {
        String name = StringUtil.getRandomString(5);
        Items items = new Items("更新名称", 9, 2, "挖矿机01", "https://1111", new Date());
        itemsDao.updateByItems(items);
    }


}
