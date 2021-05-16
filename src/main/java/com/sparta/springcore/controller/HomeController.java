package com.sparta.springcore.controller;

import com.sparta.springcore.model.Folder;
import com.sparta.springcore.security.UserDetailsImpl;
import com.sparta.springcore.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private FolderService folderService;

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        //@AuthenticationPrincipal UserDetailsImpl userDetails -- 스프링 시큐리티가 로그인된 사용자 정보를 넘겨준다.
        List<Folder> folderList = folderService.getFolders(userDetails.getUser());
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("folders", folderList);
        return "index";
    }

    @Secured("ROLE_ADMIN") //관리자만 요청할 수 있다.
    @GetMapping("/admin")
    public String admin(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("admin", true);
        return "index";
    }
}