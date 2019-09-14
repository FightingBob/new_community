package com.littlebob.community.service;

import com.littlebob.community.dto.AccessTokenDTO;
import com.littlebob.community.dto.GithubUser;
import com.littlebob.community.mapper.UserMapper;
import com.littlebob.community.model.User;
import com.littlebob.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
public class AuthorizeService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GithubProvider githubProvider;

    public void callback(String clientId, String clientSecret, String code,
                         String redirectUrl, String state,
                         HttpServletRequest request, HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_url(redirectUrl);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);

        if (null != githubUser) {
            int userId = getUserId(String.valueOf(githubUser.getId()));
            String token = UUID.randomUUID().toString();
            long currentTime = System.currentTimeMillis();
            // 修改token令牌
            if (0 != userId) {
                userMapper.updateUserByToken(userId, token, currentTime);
            }else {
                User user = new User();
                user.setName(githubUser.getName());
                user.setCountId(String.valueOf(githubUser.getId()));

                user.setToken(token);
                user.setGmtCreate(currentTime);
                user.setGmtModified(user.getGmtCreate());
                user.setBio(githubUser.getBio());
                userMapper.insert(user);
            }
            // 写入session和cookie
//            request.getSession().setAttribute("user", githubUser);
            response.addCookie(new Cookie("token", token));

        }
    }

    private int getUserId(String countId) {
        return userMapper.selectUserByCountId(countId);
    }

}
