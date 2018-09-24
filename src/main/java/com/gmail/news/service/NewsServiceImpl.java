package com.gmail.news.service;

import com.gmail.news.model.News;
import com.gmail.news.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl {

    @Autowired
    NewsRepository newsRepository;

    public List<News> findAllNews(){
        List<News> newsList = newsRepository.findAll();
        return newsList;
    }

    public List<News> findNewsByCountry(String country){
        List<News> newsList = newsRepository.findByCountry(country);
        return newsList;
    }

    public News findNewsByGeneratedUrl(String generatedUrl){
        News news = newsRepository.findByGeneratedUrl(generatedUrl);
        return news;
    }

    public News findNewsById(Long id){
        long newsId = id;
        News news = newsRepository.findById(newsId);
        return news;
    }

    public List<News> findNewsByQuery(){
        List<News> newsList = newsRepository.findAll();
        return newsList;
    }

    public Page<News> findAllNewsWithPages(Pageable pageable){
        Page<News>  newsPage = newsRepository.findAll(pageable);
        return newsPage;
    }


    public String findCountryByShort(String shortCountry){
        if(shortCountry.equals("us")){
            return "USA";
        }
        if(shortCountry.equals("mx")){
            return "Mexico";
        }
        if(shortCountry.equals("pl")){
            return "Poland";
        }
        if(shortCountry.equals("gb")){
            return "Great Britain";
        }
        if(shortCountry.equals("au")){
            return "Australia";
        }
        return "Country";

    }
}