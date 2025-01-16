package com.ohgiraffers.jenkins_test_app.advice.controller;

import com.ohgiraffers.jenkins_test_app.advice.entity.Posts;
import com.ohgiraffers.jenkins_test_app.advice.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class PostController
{
    @Autowired
    private PostService postService;

    @PostMapping("/new")
    public void insertNewPost(@RequestBody Posts post)
    {
        postService.savePost(post);
    }
}
