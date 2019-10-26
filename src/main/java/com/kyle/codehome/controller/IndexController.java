package com.kyle.codehome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: Kyle
 * @date: 2019/10/22 -  18:09
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String hello(@RequestParam(name = "name") String name, Model model){
        model.addAttribute("name",name);
        return "index";
    }
}
