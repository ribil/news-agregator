package com.gmail.news.service;

import com.gmail.news.model.News;
import com.gmail.news.model.NewsResponse;
import com.gmail.news.repository.NewsRepository;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

@Service
@EnableScheduling
public class ParserNewsServiceImpl {

    @Autowired
    NewsRepository newsRepository;

    private static final Logger log = LoggerFactory.getLogger(ParserNewsServiceImpl.class);


    @Transactional
    //@Scheduled(cron = "0 10 3 * * ?")
    public void parse() {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        String[] countries = {"us", "mx", "gb", "au", "pl"};
        /*String[] categories = {"business", "entertainment", "general",
                "health", "science", "sports", "technology"};*/

        for (int country = 0; country < 5; country++) {
            //for (int category = 0; category < 7; category++) {

                String url = "https://newsapi.org/v2/top-headlines?" +
                        "pageSize=100" +
                        //"&category=" + categories[category] +
                        "&country=" + countries[country] +
                        "&apiKey=7e6a5e980cdd4af698c57bce6c11e278";


                RestTemplate restTemplate = new RestTemplate();

                NewsResponse response = restTemplate.exchange
                                (url, HttpMethod.GET, new HttpEntity<>(createHeaders("", "")),
                                        NewsResponse.class).getBody();

                List<News> list = response.getArticles();
                log.info(response.toString());

                for (News n : list) {
                    n.setCountry(countries[country]);
                    //n.setCategory(categories[category]);


                    try {
                        String generatedUrl = URLEncoder.encode(n.getTitle(), "UTF-8");
                        n.setGeneratedUrl(generatedUrl);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    newsRepository.save(n);
                    log.info(n.toString());
                }

            }
       // }
    }

    public static HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("UTF-8")));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};

    }
}
