package com.ohgiraffers.jenkins_test_app.chatting.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class TestWebsocketController implements WebSocketConfigurer
{
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry)
    {
        //WebSocket 핸들러를 등록하는 메소드
        //여기서 socket 관리

        // 모든 출처에서 접근 허용
        registry.addHandler(new ChatWebSocketHandler(), "/chattingServer").setAllowedOrigins("*");
    }
}
