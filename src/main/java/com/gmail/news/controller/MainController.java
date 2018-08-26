package com.gmail.news.controller;

import com.gmail.news.model.News;
import com.gmail.news.service.NewsServiceImpl;
import com.gmail.news.service.ParserNewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    ParserNewsServiceImpl parserNewsService;

    @Autowired
    NewsServiceImpl newsService;

    // Выгрузка новостей
    @GetMapping("/parse")
    public String main() {

        parserNewsService.parse();
        return "main";
    }

    // Последние новости на главной
    @GetMapping("/")
    public String index(Map<String, Object> model) {

        List<News> newsList = newsService.findAllNews();
        model.put("newslist", newsList);
        return "index";
    }

    // Страница с новостями по стране
    @GetMapping("/news/{country}")
    public String countryNews(
            Map<String, Object> model,
            @PathVariable("country") String country
    ) {

        List<News> newsList = newsService.findNewsByCountry(country);
        model.put("newslist", newsList);
        return "country";
    }

    // Отдельная новость
    @GetMapping("/selected_news/{id}")
    public String specificNews(
            Map<String, Object> model,
            @PathVariable("id") Long id
    ) {

        News news = newsService.findNewsById(id);
        model.put("news", news);
        return "news";
    }

    // Страница результатов поиска

    // Поиск


}
