package com.pda.stock.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pda.stock.entity.Body;
import com.pda.stock.entity.Header;
import com.pda.stock.entity.Input;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

@Component
public class WebSocketEventListener {
    private int num=0;
    private final SimpMessagingTemplate messagingTemplate;
    private final Map<String, String> sessionIdToUserMap = new ConcurrentHashMap<>();

    private final Map<String,String> socketData = new ConcurrentHashMap<>();
    // 종목코드, 주가







    @Autowired
    public WebSocketEventListener(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }



}
