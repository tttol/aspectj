package io.github.tttol.aspectj.controller;

import io.github.tttol.aspectj.dto.UserInfoDto;
import io.github.tttol.aspectj.form.UserInfoForm;
import io.github.tttol.aspectj.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/userinfo/register")
@RequiredArgsConstructor
@Slf4j
public class UserInfoRegisterController {
    private final UserInfoService userInfoService;

    @GetMapping("/edit")
    public String edit(final Model model) {
        model.addAttribute("userInfoForm", new UserInfoForm(null, null, null));
        return "user/register/edit";
    }

    @PostMapping("/execute")
    public String execute(@ModelAttribute final UserInfoForm form) {
        final var count = userInfoService.register(UserInfoDto.generateFromForm(form));
        log.debug("{} user registered.", count);
        return "redirect:/userinfo/list";
    }
}

