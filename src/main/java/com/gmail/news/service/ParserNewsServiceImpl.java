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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

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
    public void parse() {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        String[] countries = {"us", "ru", "ua", "gb", "au"};
        String[] categories = {"business", "entertainment", "general",
                "health", "science", "sports", "technology"};

        for (int country = 0; country < 5; country++) {
            for (int category = 0; category < 7; category++) {

                String url = "https://newsapi.org/v2/top-headlines?" +
                        "pageSize=100" +
                        "&category=" + categories[category] +
                        "&country=" + countries[country] +
                        "&apiKey=450cf8e8b0004e7b809d15f6f0f4e0db";


                RestTemplate restTemplate = new RestTemplate();

                NewsResponse response = restTemplate.exchange
                        //String response = restTemplate.exchange
                                (url, HttpMethod.GET, new HttpEntity<>(createHeaders("", "")),
                                        NewsResponse.class).getBody();
                //response.getResult().forEach(System.out::println);

                List<News> list = response.getArticles();
                log.info(response.toString());

                for (News n : list) {
                    n.setCountry(countries[country]);
                    n.setCategory(categories[category]);

                    /*String newsUrl = n.getUrl();

                    StringBuilder sourceName = new StringBuilder();

                    if(newsUrl.charAt(4) == 's'
                            || newsUrl.charAt(9) == 'w'
                            || newsUrl.charAt(10) == 'w'){
                        int i = 12;
                        char c = '-';
                        while(c != '/') {
                            c = newsUrl.charAt(i);
                            sourceName.append(c);
                            i++;
                        }
                    } else if(newsUrl.charAt(4) == 's'
                            || newsUrl.charAt(8) != 'w'
                            || newsUrl.charAt(9) != 'w'){
                        int i = 9;
                        char c = '-';
                        while(c != '/'){
                            c = newsUrl.charAt(i);
                            sourceName.append(c);
                            i++;
                        }
                    } else if(newsUrl.charAt(4) == ':'
                            || newsUrl.charAt(8) == 'w'
                            || newsUrl.charAt(9) == 'w'){
                        int i = 11;
                        char c = '-';
                        while(c != '/') {
                            c = newsUrl.charAt(i);
                            sourceName.append(c);
                            i++;
                        }
                    } else if(newsUrl.charAt(4) == ':'
                            || newsUrl.charAt(7) != 'w'
                            || newsUrl.charAt(8) != 'w'){
                        int i = 7;
                        char c = '-';
                        while(c != '/') {
                            c = newsUrl.charAt(i);
                            sourceName.append(c);
                            i++;
                        }
                    }

                    n.setSourceName(sourceName.toString());*/
                    newsRepository.save(n);
                    log.info(n.toString());
                }

            }
        }
    }

    /*@Transactional
    @Scheduled(cron = "0 10 3 * * ?")
    public void parseWithUpdate() {
        String username = "72689e9c-1e0a-48b1-b06f-9548961db2ee";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        for (int i = 270000; i < 278000; i += 100) {
            String url = "https://www.reed.co.uk/api/1.0/search?resultsToSkip=" + i + "&resultsToTake=100";
            RestTemplate restTemplate = new RestTemplate();

            ReedJobResponse response = restTemplate.exchange
                    (url, HttpMethod.GET, new HttpEntity<>(createHeaders(username, "")),
                            ReedJobResponse.class).getBody();
            response.getResult().forEach(System.out::println);

            List<ReedJob> list = response.getResult();

            Calendar c1 = Calendar.getInstance();
            c1.add(Calendar.DAY_OF_YEAR, -1); // yesterday

            Calendar c2 = Calendar.getInstance();

            for (ReedJob job : list) {
                c2.setTime(job.getDate()); // vacancy date

                if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)) {

                    job.setSiteId("reed_site_id");
                    reedJobRepository.save(job);
                    log.info(job.toString());
                }



            }
        }

    }*/

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
