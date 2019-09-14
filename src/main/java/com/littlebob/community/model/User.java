package com.littlebob.community.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String countId;
    private String name;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String bio;
    private String avatarUrl;
}
