package com.dj.ssmp.pojo;

import lombok.Data;

import java.util.List;

@Data
public class UserQuery{

    private String userName;
    private List<Integer> hobbys;
    private Integer sex;
    private Integer pageNo;
    private Integer pageSize = 3;
    private List<User> list;

}
