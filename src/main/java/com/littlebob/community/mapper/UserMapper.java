package com.littlebob.community.mapper;

import com.littlebob.community.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("insert into user (name, count_id, token, gmt_create, gmt_modified, bio, avatar_url) values(#{name}, #{countId}, #{token}, #{gmtCreate}, #{gmtModified}, #{bio}, #{avatarUrl})")
    void insert(User user);

    @Select("select id from user where count_id = #{countId}")
    Integer selectUserByCountId(String countId);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Update("update user set token = #{token}, gmt_modified = #{gmtModified} where id = #{id}")
    void updateUserByToken(@Param("id") int id, @Param("token") String token, @Param("gmtModified") long modified);

    @Select("select * from user where id = #{id}")
    User select(@Param("id") int id);
}
