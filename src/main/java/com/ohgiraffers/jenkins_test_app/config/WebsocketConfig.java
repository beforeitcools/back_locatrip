package com.ohgiraffers.jenkins_test_app.config;
import com.ohgiraffers.jenkins_test_app.chatting.controller.ChatWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.util.pattern.PathPatternParser;

@Configuration
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer
{
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry)
//    {
//        //WebSocket 핸들러를 등록하는 메소드
//        //여기서 socket 관리
//
//        // 모든 출처에서 접근 허용
//        registry.addHandler(new ChatWebSocketHandler(), "/chattingServer").setAllowedOrigins("*");
//    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new ChatWebSocketHandler(), "/chattingServer/{teamId}")
                .setAllowedOrigins("*");
//                .setHandshakeHandler(new DefaultHandshakeHandler()).setAllowedOriginPatterns();
    }
}
