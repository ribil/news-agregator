package com.gmail.news.controller;

import com.gmail.news.model.News;
import com.gmail.news.service.NewsServiceImpl;
import com.gmail.news.service.ParserNewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
        if(country.equals("us")){
            model.put("country", "USA");
        }
        if(country.equals("mx")){
            model.put("country", "Mexico");
        }
        if(country.equals("pl")){
            model.put("country", "Poland");
        }
        if(country.equals("gb")){
            model.put("country", "Great Britain");
        }
        if(country.equals("au")){
            model.put("country", "Australia");
        }
        return "country";
    }

    // Отдельная новость
    @GetMapping("/selected_news/{title}")
    public String specificNews(
            Map<String, Object> model,
            @PathVariable("title") String generatedUrl
    ) {
        String url = new String();
        try {
            url = URLEncoder.encode(generatedUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        News news = newsService.findNewsByGeneratedUrl(url);
        model.put("news", news);
        return "newspage";
    }

    // Страница результатов поиска

    // Поиск


}
