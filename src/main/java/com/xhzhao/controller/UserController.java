package com.xhzhao.controller;

import com.xhzhao.domain.Result;
import com.xhzhao.domain.User;
import com.xhzhao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(path = {"/"}, method = RequestMethod.GET)
    public String login(Model model){
        return "login";
    }

    @RequestMapping(path = {"/user/login"}, method = RequestMethod.POST)
    public String login(User user, RedirectAttributes attributes){
        Result result = userService.login(user);
        if (result.isSuccess()){
            return "redirect:/show";
        }
        else return "redirect:/";
    }

    @RequestMapping(path = {"/user/register"}, method = RequestMethod.POST)
    public Result register(User user){
        return userService.register(user);
    }

    @RequestMapping(path = {"/login.html"}, method = RequestMethod.GET)
    public String register(Model model){
        return "redirect:/login.html";
    }
}
