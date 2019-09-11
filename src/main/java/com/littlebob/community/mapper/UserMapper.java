package com.littlebob.community.mapper;

import com.littlebob.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    @Insert("insert into user (name, count_id, token, gmt_create, gmt_modified) values(#{name}, #{countId}, #{token}, #{gmtCreate}, #{gmtModified})")
    void insert(User user);
}
