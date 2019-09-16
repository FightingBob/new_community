package com.littlebob.community.dto;

import com.littlebob.community.model.Question;
import com.littlebob.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Question question;
    private String name;
    private String avatarUrl;
    private Integer userId;
}
