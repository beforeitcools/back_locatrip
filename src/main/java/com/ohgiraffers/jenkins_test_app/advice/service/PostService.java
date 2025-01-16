package com.ohgiraffers.jenkins_test_app.advice.service;

import com.ohgiraffers.jenkins_test_app.advice.entity.Posts;
import com.ohgiraffers.jenkins_test_app.advice.repository.PostRepository;
import com.ohgiraffers.jenkins_test_app.common.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService
{
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SecurityUtil securityUtil;

    public void savePost(Posts post)
    {
        post.setUserId(securityUtil.getAuthenticatedUser().getId());
        postRepository.save(post);
    }
}
