package com.ohgiraffers.jenkins_test_app.chatting.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/link-preview")
public class LinkPreviewController
{
    @PostMapping
    public Map<String, String> getLinkPreview(@RequestParam String url) {
        try {
            Document doc = Jsoup.connect(url).get();

            String title = doc.select("meta[property=og:title]").attr("content");
            String description = doc.select("meta[property=og:description]").attr("content");
            String image = doc.select("meta[property=og:image]").attr("content");

            if (title.isEmpty()) {
                title = doc.title();
            }

            Map<String, String> previewData = new HashMap<>();
            previewData.put("title", title);
            previewData.put("description", description);
            previewData.put("image", image);
            previewData.put("url", url);

            return previewData;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid URL");
        }
    }
}
