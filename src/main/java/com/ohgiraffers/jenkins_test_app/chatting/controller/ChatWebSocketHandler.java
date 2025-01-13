package com.ohgiraffers.jenkins_test_app.chatting.controller;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ChatWebSocketHandler extends TextWebSocketHandler
{
    private static final Set<WebSocketSession> clients = Collections.synchronizedSet(new HashSet<>());
    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception
    {
        // 내가 저장한 메세지 string으로 받아옴 json parser로 map 변환 해서 front에 넘겨주기
        String payload = message.getPayload();
        try{
            System.out.println("메세지 출력: " + session.getId() + ": " + payload);
            synchronized (clients)
            {
                for(WebSocketSession client : clients)
                {
                    if(!client.equals(session))
                    {
                        // 자기 자신 제외하고 메세지 전송
                        client.sendMessage(new TextMessage(payload)); //
                    }
                }
            }
        } catch (Exception e){
            System.err.println("Error handling message: " + e.getMessage());
            e.printStackTrace();
        }
        // 클라이언트로부터 텍스트 메세지를 수신햇을 때 호출되는 메소드 얘가 왜 안오는거지

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception
    {
        // 클라이언트가 WebSocket 연결을 성공적으로 수행했을 때 호출되는 메소드
        sessions.put(session.getId(), session);
        clients.add(session);
        System.out.println("웹소켓 연결: " + session.getId() + " 세션: " + session);
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception
    {
        // 통신 중 에러가 발생했을 때 호출되는 메소드
        System.out.println("에러 발생: " + session.getId());
        exception.printStackTrace();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception
    {
        // 클라이언트가 websocket 연결을 닫았을 때 호출되는 메소드

        sessions.remove(session.getId());
        clients.remove(session);
        System.out.println("웹소켓 종료: " + session.getId());
    }

//    @Override
//    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception
//    {
//        //이쪽으로 넘어왔다고?
//        session.sendMessage(message);
//        synchronized (clients)
//        {
//            for(WebSocketSession client : clients)
//            {
//                System.out.println(client + " <- 이것은 나의 클라이언트");
//                if(!client.equals(session))
//                {
//                    // 자기 자신 제외하고 메세지 전송
//                    System.out.println("메세지 출력: " + session.getId() + ": " + message.getPayload());
//                    //client.sendMessage(new TextMessage((String) message.getPayload()));
//                }
//            }
//        }
//    }


}
