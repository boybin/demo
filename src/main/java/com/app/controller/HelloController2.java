package com.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/hello2")
public class HelloController2 {

    @RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		model.addAttribute("title", "Hello world!");
		return "hello2";
	}


}