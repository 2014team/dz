package com.artcweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RootController {

	@RequestMapping("{param}")
    public String main(@PathVariable("param") String param) {
        System.out.println("进入Maincontroller");
        return param;
    }
}
