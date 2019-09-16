package com.littlebob.community.service;

import com.littlebob.community.dto.PageDTO;
import com.littlebob.community.dto.QuestionDTO;
import com.littlebob.community.mapper.QuestionMapper;
import com.littlebob.community.mapper.UserMapper;
import com.littlebob.community.model.Question;
import com.littlebob.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class IndexService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public List<QuestionDTO> index(HttpServletRequest request, int currentPage, int pageSize) {
        setSession(request, currentPage, pageSize);
        return getQuestionList(currentPage, pageSize);

    }

    public User getUser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies && 0 != cookies.length) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    return userMapper.findByToken(token);
                }
            }
        }
        return null;
    }

    public boolean hasLogined(HttpServletRequest request) {
        if (null != getUser(request)) {
            return true;
        }
        return false;
    }

    private List<QuestionDTO> getQuestionList(int currentPage, int pageSize) {
        int offset = pageSize*(currentPage-1);
        List<Question> questions = questionMapper.selectList(offset, pageSize);
        ArrayList<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.select(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setQuestion(question);
            questionDTO.setUserId(user.getId());
            questionDTO.setName(user.getName());
            questionDTO.setAvatarUrl(user.getAvatarUrl());
            questionDTOS.add(questionDTO);
        }
        return questionDTOS;
    }

    private PageDTO getPageDTO(int currentPage, int pageSize){
        int count = questionMapper.selectCount();
        if (count == 0) {
            return null;
        }
        PageDTO pageDTO = new PageDTO();
        pageDTO.setCount(count);
        int lastPage = count % pageSize > 0 ? count/pageSize + 1 : count/pageSize;
        pageDTO.setLastPage(lastPage);
        pageDTO.setPageSize(pageSize);
        pageDTO.setCurrentPage(currentPage);
        if (currentPage > 1) {
            pageDTO.setPrePage(currentPage-1);
        }
        if (currentPage < lastPage) {
            pageDTO.setNextPage(currentPage + 1);
        }
        if (lastPage <= 5) {
            pageDTO.setFirstPage(1);
            pageDTO.setEndPage(lastPage);
        }else {
            if (currentPage >= 3) {
                if ((lastPage - currentPage) >= 2) {
                    pageDTO.setFirstPage(currentPage - 2);
                    pageDTO.setEndPage(currentPage + 2);
                }else {
                    pageDTO.setEndPage(lastPage);
                    pageDTO.setFirstPage(lastPage - currentPage + 1);
                }
            }else {
                pageDTO.setFirstPage(1);
                pageDTO.setEndPage(5);
            }
        }
        return pageDTO;
    }

    private void setSession(HttpServletRequest request, int currentPage, int pageSize) {
        request.getSession().setAttribute("pagination", getPageDTO(currentPage, pageSize));
        if (null == request.getSession().getAttribute("user")) {
            User user = getUser(request);
            if (null != user) {
                request.getSession().setAttribute("user", user);
            }
        }
    }
}
