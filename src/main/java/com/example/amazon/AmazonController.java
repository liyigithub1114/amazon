package com.example.amazon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AmazonController {

    public static final String URL_PREFIX = "url=";

    @Autowired
    private RestTemplate restTemplate;

    /**
     * https://www.amazon.com/Best-Sellers-Womens-Jumpsuits-Rompers-Overalls/zgbs/fashion/9522930011/ref=zg_bs_nav_3_1040660
     * https://www.amazon.com/Best-Sellers-Womens-Jumpsuits-Rompers-Overalls/zgbs/fashion/9522930011/ref=zg_bs_pg_2?_encoding=UTF8&pg=2
     */
    @GetMapping("")
    public String parse(HttpServletRequest request) {

        final String queryString = request.getQueryString();
        String url = queryString.replace(URL_PREFIX, "");
        final HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91"
                        + ".0.4472.114 Safari/537.36");

        //Create a new HttpEntity
        final HttpEntity<String> entity = new HttpEntity<>(headers);

        //Execute the method writing your HttpEntity to the request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return parse(response.getBody());
        }

        throw new IllegalStateException("please try again!");

    }

    private String parse(String content) {
        final List<String> highPrice = AmazonPatternUtils.findHighPrice(content);
        final List<String> star = AmazonPatternUtils.findStar(content);
        final List<String> comment = AmazonPatternUtils.findComment(content);

        int size = Math.max(Math.max(highPrice.size(), star.size()), comment.size());

        StringBuilder sb = new StringBuilder();
        String template = "%s: high price: %s, star: %s, comment: %s</br>";

        for (int i = 0; i < size; i++) {
            sb.append(String.format(template, i + 1, highPrice.get(i), star.get(i), comment.get(i)));
        }
        return sb.toString();
    }

}