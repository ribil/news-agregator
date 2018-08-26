package com.gmail.news.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsResponse {

    @JsonProperty("totalResults")
    private int totalResults;

    @JsonProperty("articles")
    private List<News> articles;

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<News> getArticles() {
        return articles;
    }

    public void setArticles(List<News> articles) {
        this.articles = articles;
    }

}
