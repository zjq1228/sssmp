package com.dj.ssmp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/")
public class UserPageController {

    @RequestMapping("toShow")
    public String toShow(){
        return "show";
    }


    @RequestMapping("toAdds")
    public String toAdds(){
        return "adds";
    }

}
