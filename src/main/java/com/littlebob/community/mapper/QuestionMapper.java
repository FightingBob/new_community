package com.littlebob.community.mapper;

import com.littlebob.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title, description, creator, gmt_create, gmt_modified, view_count, comment_count, like_count, tag) values(#{title}, #{description}, #{creator}, #{gmtCreate}, #{gmtModified}, #{viewCount}, #{commentCount}, #{likeCount}, #{tag})")
    void insert(Question question);

    @Select("select * from question order by gmt_create desc limit #{offset} , #{count}")
    List<Question> selectList(@Param("offset") int offset, @Param("count") int count);

    @Select("select count(*) from question")
    Integer selectCount();
}
