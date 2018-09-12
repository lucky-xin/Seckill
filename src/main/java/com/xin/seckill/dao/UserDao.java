package com.xin.seckill.dao;

import biz.datainsights.utils.CollectionUtil;
import biz.datainsights.utils.StringUtil;
import com.xin.seckill.mybatis.handlers.SexEnumTypeHandler;
import com.xin.seckill.pojo.User;
import com.xin.seckill.pojo.UserQueryVo;
import com.xin.seckill.util.DynamicSQLUtil;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 用户操作dao
 * @date 2018-08-11 20:33
 * @Copyright (C)2018 , Luchaoxin
 */
@Mapper
public interface UserDao {

    @Insert("insert into user_info(id,name,password,email,birth,address,sex)values"
            + "(#{id},#{name},#{password},#{email},#{birth,jdbcType=TIMESTAMP},#{address},"
            + "#{sex,typeHandler=com.xin.seckill.mybatis.handlers.SexValueTypeHandler})")
    int insertUser(User user) throws Exception;

    int batchInsertUser(@Param("users") List<User> users) throws Exception;

    int batchDeleteUser(List<Integer> ids) throws Exception;
    
    @SelectProvider(type = UserDaoProvider.class, method = "findUserList")
    @Results(id = "sexTypeHandler", value = @Result(property = "sex", column = "sex", typeHandler = SexEnumTypeHandler.class))
    List<User> findUserList(UserQueryVo queryVo) throws Exception;

    class UserDaoProvider {
        public String findUserList(UserQueryVo queryVo) {
            return new SQL() {
                {
                    SELECT("id", "name", "password", "email", "birth", "address", "sex");
                    FROM("user_info");
                    if (queryVo.getUserCustom() != null && !StringUtil.isNull(queryVo.getUserCustom().getSex())) {
                        WHERE("sex=#{userCustom.sex,typeHandler=com.xin.seckill.mybatis.handlers.SexValueTypeHandler}");
                    }
                    if (!StringUtil.isNull(queryVo.getUserCustom()) && !StringUtil.isEmpty(queryVo.getUserCustom().getName())) {
                        WHERE(DynamicSQLUtil.getLikeStmt("name", queryVo.getUserCustom().getName()));
//                        WHERE("name like CONCAT('%',#{userCustom.name},'%')");
                    }
                    List<Integer> ids = queryVo.getIds();
                    if (!CollectionUtil.isEmpty(ids)) {
                        WHERE(DynamicSQLUtil.getInStmt("id", ids.toArray()));
                    }
                }
            }.toString();
        }
    }

    @Select("select count(*) from user_info where sex=#{userCustom.sex,typeHandler=com.xin.seckill.mybatis.handlers.SexValueTypeHandler} " +
            "and name like '%${userCustom.name}%'")
    int findUserCount(UserQueryVo queryVo) throws Exception;

    @Select("select id as id_, name as username_,birth as birthday_ from user_info where id=#{value}")
    @Results(id = "findUserByResultMap", value = {
            @Result(column = "id_", property = "id"),
            @Result(column = "username_", property = "name"),
            @Result(column = "birthday_", property = "birth")}
    )
    User findUserByResultMap(@Param("value") int id) throws Exception;

    @Select("select id,name,password,email,birth,address,sex from user_info where id=#{id}")
    @ResultMap("sexTypeHandler")
    User findUserById(@Param("id") int id) throws Exception;

    @Select("select id,name,password,email,birth,address,sex from user_info where name like '%${name}%'")
    @ResultMap("sexTypeHandler")
    List<User> findUserByName(@Param("name") String name) throws Exception;

    @Delete("delete from user_info where id=#{id}")
    int deleteUser(@Param("id") int id) throws Exception;

    @Update("update user_info set sex=#{sex,typeHandler=com.xin.seckill.mybatis.handlers.SexValueTypeHandler},name=#{name},"
            + "birth=#{birth,jdbcType=TIMESTAMP},address=#{address},password=#{password} ,email=#{email} where id=#{id}")
    int updateUser(User user) throws Exception;

    @Select("select id,name,password,email,birth,address,sex from user_info where id=#{id} and name like '%${username}%'")
    @ResultMap("sexTypeHandler")
    List<User> findUserByHashMap(Map<String, Object> map) throws Exception;


    List<User> findUserByMap(UserQueryVo userQueryVo) throws Exception;

    @Update("update user_info set sex = #{sex} where id/2=0")
    int updateSex(@Param("sex") int sex) throws Exception;

    @Select("select id,name,password,email,birth,address,sex from user_info")
    @ResultMap("sexTypeHandler")
    List<User> pagingQueryUserList(RowBounds rowBounds);

    @Select("select id,name,password,email,birth,address,sex from user_info where password=#{password} and name=#{name}")
    @ResultMap("sexTypeHandler")
    int findUserByNameAndPassword(User user);

}
