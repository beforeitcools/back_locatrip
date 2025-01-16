package com.ohgiraffers.jenkins_test_app.advice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public class PostsWithMyLocalAreaDTO {

    private Integer postId;
    private String title;
    private List<String> selectedRegionsList;
    private String region;
    private Long regionCount;
}
