package com.littlebob.community.mapper;

import com.littlebob.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into user (name, count_id, token, gmt_create, gmt_modified, bio) values(#{name}, #{countId}, #{token}, #{gmtCreate}, #{gmtModified}, #{bio})")
    void insert(User user);

    @Select("select id from user where count_id = #{countId}")
    int selectUserByCountId(String countId);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Update("update user set token = #{token}, gmt_modified = #{gmtModified} where id = #{id}")
    void updateUserByToken(@Param("id") int id, @Param("token") String token, @Param("gmtModified") long modified);
}
