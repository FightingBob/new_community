package com.littlebob.community.mapper;

import com.littlebob.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("insert into user (name, count_id, token, gmt_create, gmt_modified) values(#{name}, #{countId}, #{token}, #{gmtCreate}, #{gmtModified})")
    void insert(User user);

    @Select("select id from user where count_id = #{countId}")
    int select(String countId);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);
}
