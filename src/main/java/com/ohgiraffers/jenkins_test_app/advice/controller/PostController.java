package com.ohgiraffers.jenkins_test_app.advice.controller;

import com.ohgiraffers.jenkins_test_app.advice.entity.Posts;
import com.ohgiraffers.jenkins_test_app.advice.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController
{
    @Autowired
    private PostService postService;

    @PostMapping("/new")
    public ResponseEntity<Integer> insertNewPost(@RequestBody Posts post)
    {
        return ResponseEntity.ok(postService.savePost(post));
    }

    @GetMapping("/select/{postId}")
    public ResponseEntity<Posts> getPostById(@PathVariable int postId)
    {
        System.out.println("GETPOSTBYID로 접근은 햇냐고");
        return postService.getPostById(postId);
    }
}
