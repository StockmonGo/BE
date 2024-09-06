package com.pda.stock.controller;

import com.pda.stock.websocket.WebSocketEventListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {
    private WebSocketEventListener webSocketEventListener;

    public StockController(WebSocketEventListener webSocketEventListener){
        this.webSocketEventListener = webSocketEventListener;
    }

    @MessageMapping("/{id}")
    @SendTo("/{id}/greetings") // "topic/greetings"를 핸들러에서 처리를 마친 메시지를 구독하고 있는 모든 구독자에게 메시지 푸시
    public void greeting(@DestinationVariable String id) throws Exception {
        System.out.println("stock controller: " + id);

        // 소켓 및 주식 관련 작업
        webSocketEventListener.connectSocket(id);
        webSocketEventListener.connectStock(id);
    }


}
