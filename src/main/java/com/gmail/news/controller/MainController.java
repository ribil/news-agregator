package com.gmail.news.controller;

import com.gmail.news.service.ParserNewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    ParserNewsServiceImpl parserNewsService;

    @GetMapping("/")
    public String main() {

        parserNewsService.parse();
        return "main";
    }


}
