package com.xin.seckill.dao;

import com.xin.seckill.pojo.Items;
import com.xin.seckill.util.DynamicSQLUtil;
import org.apache.ibatis.annotations.*;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: Item 操作dao
 * @date 2018-08-12 10:07
 * @Copyright (C)2018 , Luchaoxin
 */

@Mapper
public interface ItemsDao {

    @Insert("insert into items (id, name, price,pic, createtime, detail) values " +
            "(#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, #{price,jdbcType=REAL},#{pic,jdbcType=VARCHAR}," +
            "#{createtime,jdbcType=TIMESTAMP}, #{detail,jdbcType=LONGVARCHAR})")
    int insert(Items record);

    @Delete("delete from items where id = #{id,jdbcType=INTEGER}")
    int deleteByPrimaryKey(Integer id);

    @Update("update items set name = #{name},price = #{price},pic = #{pic},createtime = #{createtime} where id = #{id}")
    int updateByPrimaryKey(Items record);

    @UpdateProvider(type = ItemsDaoProvider.class, method = "updateByItems")
    int updateByItems(@Param("record") Items record);

    class ItemsDaoProvider {
        public String updateByItems(Items record) throws NoSuchFieldException {
            return "<script>" + DynamicSQLUtil.getAndStmt(Items.class) + "</script>";
        }
    }
}
