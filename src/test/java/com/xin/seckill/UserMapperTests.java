package com.xin.seckill;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.xin.seckill.dao.UserDao;
import com.xin.seckill.enums.Sex;
import com.xin.seckill.pojo.UserInfo;
import com.xin.seckill.pojo.UserQueryVo;
import com.xin.utils.StringUtil;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: Mybatis测试
 * @date 2018-08-11 23:06
 * @Copyright (C)2018 , Luchaoxin
 */
@RunWith(SpringRunner.class)
//告诉junit spring的配置文件
//@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
@SpringBootTest(classes = SeckillApplication.class)
public class UserMapperTests {

    @Autowired
    private UserDao userDao;

    @Autowired
    private SqlSession sqlSession;

    @Autowired
    private PageHelper pageHelper;

    @Test
    public void testInsertUser() throws Exception {
        int count = 40;
        for (int i = 0; i < count; i++) {
            String randomString = StringUtil.getRandomString(10);
            //Integer id, String name, String password, String email, Date birth, String imageId, String address, Sex sex
            UserInfo user = new UserInfo(i, "name" + i + randomString, randomString, randomString + "@qq.com", new Date(), randomString, "长沙", Sex.MALE);
            user.setId(5 + i);
            userDao.insertUser(user);
        }
    }

    @Test
    public void testUpdate() throws Exception {
        userDao.updateSex(1);
    }

    @Test
    public void batchInsertUserTest() throws Exception {
        int count = 10;
        List<UserInfo> users = Lists.newArrayList();
        for (int i = 0; i < count; i++) {
            String randomString = StringUtil.getRandomString(10);
            UserInfo user = new UserInfo(i, "name" + i + randomString, randomString, randomString + "@qq.com", new Date(), randomString, "长沙", Sex.MALE);
            user.setId(33333 + i);
            users.add(user);
        }
        int result = userDao.batchInsertUser(users);
        System.out.println(result);

    }

    @Test
    public void batchDeleteUserTest() throws Exception {
        int count = 10;
        List<Integer> ids = Lists.newArrayList();
        for (int i = 0; i < count; i++) {
            ids.add(33333 + i);
        }
        userDao.batchDeleteUser(ids);

    }

    @Test
    public void testFindUserById() throws Exception {
        UserInfo user = userDao.findUserById(5);
        //User [id=42, name=Ivan, password=123, email=1314@qq.com, sex=男, birth=Thu Jan 01 00:00:00 CST 1970, address=长沙]
        System.out.println(user);
    }

    @Test
    public void testFindUserByResultMap() throws Exception {
        Map<String, Object> params = Maps.newHashMap();
        params.put("id", 100);
        params.put("name", "name");
        List<UserInfo> user = userDao.findUserByHashMap(params);
        System.out.println(user);
    }

    @Test
    public void testFindUserByNames() throws Exception {
        List<UserInfo> user = userDao.findUserByName("name");
        System.out.println(user.get(0));
    }

    @Test
    public void testPageQuery() throws Exception {
        Page page = pageHelper.startPage(1, 10);
        List<UserInfo> users = userDao.findUserByName("name");
        PageInfo<UserInfo> pageInfo = new PageInfo<>(users);
        List<UserInfo> result = pageInfo.getList();
        System.out.println(result.size());
    }

    // 综合查询测试
    @Test
    public void testFindUserList() throws Exception {
        UserQueryVo userQueryVo = new UserQueryVo();
        UserInfo custom = new UserInfo();
        //foreach测试
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(91);
        ids.add(92);
        ids.add(93);
        ids.add(94);

        custom.setSex(Sex.MALE);
        custom.setName("name");
        userQueryVo.setUserCustom(custom);
        userQueryVo.setIds(ids);
        List<UserInfo> customs = userDao.findUserList(userQueryVo);
        System.out.println(customs);
    }

    // 综合查询测试
    @Test
    public void testFindUserListByMap() throws Exception {
        UserQueryVo userQueryVo = new UserQueryVo();
        UserInfo custom = new UserInfo();
        //foreach测试
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(91);
        ids.add(92);
        ids.add(93);
        ids.add(94);

        custom.setSex(Sex.MALE);
        custom.setName("name");
        userQueryVo.setUserCustom(custom);
        userQueryVo.setIds(ids);
        List<UserInfo> customs = userDao.findUserByMap(userQueryVo);
        System.out.println(customs);
    }

    @Test
    public void testFindUserByHashMap() throws Exception {
        //构造查询条件Hashmap对象
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", 1);
        map.put("username", "泡泡");

        //传递HashMap对象查询用户列表
        List<UserInfo> list = userDao.findUserByHashMap(map);
        System.out.println(list);
    }

    @Test
    public void testFindUserCount() throws Exception {
        UserQueryVo userQueryVo = new UserQueryVo();
        UserInfo custom = new UserInfo();
        custom.setSex(Sex.MALE);
        custom.setName("name");
        userQueryVo.setUserCustom(custom);
        int count = userDao.findUserCount(userQueryVo);
        System.out.println(count);
    }

    @Test
    public void testUpdateUser() throws Exception {
        UserInfo user = new UserInfo();
        user.setId(48);
        int result = userDao.updateUser(user);
        System.out.println(result);
    }

    @Test
    public void testDeleteUser() {

        try {
            userDao.deleteUser(10000);
        } catch (Exception e) {
            e.printStackTrace();
//            sqlSession.rollback();
        } finally {
//            sqlSession.close();
        }
    }

    //一级缓存: 也就SqlSession级的缓存(默认开启) SqlSession执行相同sql语句，第一次查询完毕会将数据库中查询的数据写入缓存，之后
    //此后再用此SqlSession执行相同的语句会从缓存之中读取数据
    //session commit或close后,缓存就会被清空
    @Test
    public void testCache() throws Exception {
//        //只执行一次查询 select *from user_tbl where id=?
//        User user1;
//        User user2;
//        user1 = userMapper.findUserById(42);
//        System.out.println(user1);
//        user2 = userMapper.findUserById(42);
//        System.out.println(user2);
//        System.out.println(user2==user1);
    }

    //二级缓存测试 namespace="com.mybatis.mapper.UserMapper"级
    //多个SqlSession执行相同的语句只会执行第一次请求，之后从缓存之中读取数据
    @Test
    public void testCache1() throws Exception {
//        List<User> users1;
//        List<User> users2;
//        List<User> users3;
//        //只执行一次select *from user_tbl where name like '%冰冰%'  跨session
//        SqlSession sqlSession1 = MySqlSessionFactory.openSqlSession();
//        UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);
//        users1 = userMapper1.findUserByName("冰冰");
//        System.out.println(users1);
//        sqlSession1.close();
//
//        SqlSession sqlSession2 = MySqlSessionFactory.openSqlSession();
//        UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);
//        users2 = userMapper2.findUserByName("冰冰");
//        System.out.println(users2);
//        System.out.println();
//        sqlSession2.close();
//
//        SqlSession sqlSession3 = MySqlSessionFactory.openSqlSession();
//        UserMapper userMapper3 = sqlSession3.getMapper(UserMapper.class);
//        users3 = userMapper3.findUserByName("冰冰");
//        System.out.println(users3);
//        sqlSession3.close();
    }

    @Test
    public void pagingUserListTest() {
        int offset = 0;//开始位置
        int limit = 25;//取出的数据条数
        RowBounds rowBounds = new RowBounds(offset, limit);
        List<UserInfo> users = userDao.pagingQueryUserList(rowBounds);
        System.out.println(users.size());
        sqlSession.select("", new ResultHandler<UserInfo>() {

            @Override
            public void handleResult(ResultContext<? extends UserInfo> resultContext) {
                UserInfo user = resultContext.getResultObject();
            }
        });

    }


}
