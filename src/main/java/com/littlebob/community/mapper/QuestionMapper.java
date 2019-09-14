package com.littlebob.community.mapper;

import com.littlebob.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title, description, creator, gmt_create, gmt_modified, view_count, comment_count, like_count, tag) values(#{title}, #{description}, #{creator}, #{gmtCreate}, #{gmtModified}, #{viewCount}, #{commentCount}, #{likeCount}, #{tag})")
    void insert(Question question);
}
