package com.github.binarywang.demo.wx.cp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestPageController
{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping({"/page"})
    public String testOne(Model model)
    {
        model.addAttribute("test", "teststring");

        return "index";
    }

    @RequestMapping({"/page/{agentId}"})
    public String jsApiPageTest(@PathVariable Integer agentId,Model model)
    {
        model.addAttribute("agentId", agentId);

        return "jsapi";
    }

}
