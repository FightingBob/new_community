package com.littlebob.community.service;

import com.littlebob.community.mapper.QuestionMapper;
import com.littlebob.community.model.Question;
import com.littlebob.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class PublishService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private IndexService indexService;

    private int success = 1;
    private int failure = 0;

    public int publish(String title, String description, String tag, HttpServletRequest request) {

        User user = indexService.getUser(request);
        if (null == user) {
            return failure;
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setCreator(user.getId());
        question.setLikeCount(0);
        question.setCommentCount(0);
        question.setViewCount(0);
        questionMapper.insert(question);
        return success;
    }

    public boolean hasLogined(HttpServletRequest request) {
        return indexService.hasLogined(request);
    }
}
