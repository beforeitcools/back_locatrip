package com.ohgiraffers.jenkins_test_app.user.service;


import com.ohgiraffers.jenkins_test_app.user.entity.Menu;
import com.ohgiraffers.jenkins_test_app.user.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> selectAllMenu() {

        List<Menu> menuList = menuRepository.findAll();
        if(menuList.isEmpty()){
            return null;
        }
        return menuList;
    }
}
