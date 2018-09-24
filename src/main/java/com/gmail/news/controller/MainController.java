package com.gmail.news.controller;

import com.gmail.news.model.News;
import com.gmail.news.service.NewsServiceImpl;
import com.gmail.news.service.ParserNewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String index(Map<String, Object> model,
                        @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {

        Page<News> newsPage = newsService.findAllNewsWithPages(pageable);
        model.put("page", newsPage);
        model.put("url", "/");

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

        String countryName = newsService.findCountryByShort(country);
        model.put("country", countryName);

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

    // Поиск
    @PostMapping("search")
    public String searchForm(
            Map<String, Object> model,
            @RequestParam("text") String text
    ){

        List<News> newsList = parserNewsService.searchNews(text);

        model.put("newslist", newsList);

        return "search";
    }
}
