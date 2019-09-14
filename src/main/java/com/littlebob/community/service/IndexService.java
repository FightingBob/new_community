package com.littlebob.community.service;

import com.littlebob.community.mapper.UserMapper;
import com.littlebob.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class IndexService {

    @Autowired
    private UserMapper userMapper;

    public void index(HttpServletRequest request) {
        User user = getUser(request);
        if (null != user) {
            request.getSession().setAttribute("user", user);
        }

    }

    public User getUser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
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
}
