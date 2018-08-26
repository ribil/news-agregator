package com.gmail.news.service;

import com.gmail.news.model.News;
import com.gmail.news.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public News findNewsById(Long id){
        long newsId = id;
        News news = newsRepository.findById(newsId);
        return news;
    }

    public List<News> findNewsByQuery(){
        List<News> newsList = newsRepository.findAll();
        return newsList;
    }
}
