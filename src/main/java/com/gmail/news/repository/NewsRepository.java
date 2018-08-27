package com.gmail.news.repository;

import com.gmail.news.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findByCountry(String country);

    News findById(long id);

    News findByGeneratedUrl(String generatedUrl);
}
