package com.ohgiraffers.jenkins_test_app.menu.controller;

import com.ohgiraffers.jenkins_test_app.menu.entity.Menu;
import com.ohgiraffers.jenkins_test_app.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/select")
    public ResponseEntity selectAllMenu() {

        List<Menu> menuList = menuService.selectAllMenu();
        if(menuList != null){
            return ResponseEntity.ok(menuList);
        }else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 오류 발생");
        }
    }
}
