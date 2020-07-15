package com.dj.ssmp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dj.ssmp.pojo.ResultModel;
import com.dj.ssmp.pojo.User;
import com.dj.ssmp.pojo.UserQuery;
import com.dj.ssmp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findById")
    public ResultModel findById(Integer id){
        User user = userService.getById(id);
        return new ResultModel().success(user);
    }

    /**
     * 展示
     * @param UserQuery
     * @return
     */
    @RequestMapping("/show")
    public ResultModel show(UserQuery UserQuery) {
        Map<String, Object> map = new HashMap<>();
        try {
            //当前页，每页条数
            IPage iPage = new Page(UserQuery.getPageNo(),UserQuery.getPageSize());
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            if (UserQuery.getSex() != null){
                queryWrapper.eq("sex",UserQuery.getSex());
            }
            if (!UserQuery.getUserName().isEmpty()){
                queryWrapper.like("user_name",UserQuery.getUserName());
            }
            if (UserQuery.getHobbys() !=null) {
                queryWrapper.in("hobby", UserQuery.getHobbys());
            }
            queryWrapper.inSql("user_name", "select user_name from user");
            IPage pageInfo = userService.page(iPage, queryWrapper);
            map.put("pages",pageInfo.getPages());//总页数
            map.put("list",pageInfo.getRecords());//数据结果
            return new ResultModel().success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error("服务器异常，请稍后再试");
        }
    }

    /**
     * 新增
     *
     * @param user 用户信息
     * @return
     */
    @RequestMapping("/insert")
    public ResultModel insert(User user) {
        try {
            // 新增并返回主键
            userService.save(user);
            return new ResultModel().success(user);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error(e.getMessage());
        }
    }

    /**
     * 根据主键ID修改用户信息
     *
     * @param user 用户信息
     * @return
     */
    @RequestMapping("/update")
    public ResultModel update(User user) {
        try {
            // 根据主键ID更新
            userService.updateById(user);
            return new ResultModel().success(true);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error(e.getMessage());
        }
    }

    /**
     * 根据主键删除用户信息
     *
     * @param id 主键ID
     * @return
     */
    @RequestMapping("/delete/{id}")
    public ResultModel delete(@PathVariable Integer id) {
        try {
            userService.removeById(id);
            return new ResultModel().success(true);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error(e.getMessage());
        }
    }

    /**
     * 根据主键-批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delIds")
    public ResultModel delIds(@RequestParam("ids[]")Integer[] ids) {
        try {
            List<Integer> list = Arrays.asList(ids);
            userService.removeByIds(list);
            return new ResultModel().success(true);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error(e.getMessage());
        }
    }

    /**
     * 批量新增
     *
     * @return
     */
    @RequestMapping("/batchInsert")
    public ResultModel batchInsert(UserQuery UserQuery) {
        try {
            List<User> userList = new ArrayList<User>();
            for (User user : UserQuery.getList()) {
                if (StringUtils.isEmpty(user.getUserName())) {
                    continue;
                }
                userList.add(user);
            }
            // 批量新增
            userService.saveBatch(userList);
            return new ResultModel().success(true);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel().error(e.getMessage());
        }
    }

}
