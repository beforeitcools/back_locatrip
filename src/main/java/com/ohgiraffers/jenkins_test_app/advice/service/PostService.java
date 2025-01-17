package com.ohgiraffers.jenkins_test_app.advice.service;

import com.ohgiraffers.jenkins_test_app.advice.entity.Posts;
import com.ohgiraffers.jenkins_test_app.advice.repository.PostRepository;
import com.ohgiraffers.jenkins_test_app.common.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService
{
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SecurityUtil securityUtil;

    public int savePost(Posts post)
    {
        post.setUserId(securityUtil.getAuthenticatedUser().getId());
        return postRepository.save(post).getId();
    }

    public ResponseEntity<Posts> getPostById(int postId)
    {
        System.out.println("아 존나 잠온다 get Post by Id");
        Optional<Posts> result = postRepository.findById(postId);
        System.out.println("result: " + result);
        return ResponseEntity.ok(result.get());
    }
}
