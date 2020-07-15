package com.dj.ssmp.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")//实体类对应的数据库名
public class User {

    @TableId(type = IdType.AUTO)//主键递增
    private Integer id;
    private String userName;
    private String userPwd;
    private Integer age;
    /**
     * 1男 2女
     */
    private Integer sex;
    private String email;
    /**
     * 1运动 2看书 3游戏 6雷霆嘎巴  7 R3 4哇哈哈 5乌漆嘛黑
     */
    private Integer hobby;

}
