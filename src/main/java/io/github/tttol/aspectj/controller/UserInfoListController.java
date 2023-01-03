package io.github.tttol.aspectj.controller;

import io.github.tttol.aspectj.dto.UserInfoDto;
import io.github.tttol.aspectj.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/userinfo/list")
public class UserInfoListController {
    private final UserInfoService userInfoService;

    @GetMapping
    public String list(final Model model) {
        final var userInfoDtos = this.userInfoService.getAllUsers();
        final var userInfoForms = userInfoDtos.stream().map(UserInfoDto::toForm).toList();
        model.addAttribute("userInfoForms", userInfoForms);
        return "user/list";
    }
}
