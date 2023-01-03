package io.github.tttol.aspectj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/userinfo/update")
public class UserInfoUpdateController {
    @GetMapping("edit")
    public String edit() {
        return "user/update/edit";
    }

    @PostMapping("/execute")
    public String execute() {
        return "redirect:/userinfo/list";
    }
}
