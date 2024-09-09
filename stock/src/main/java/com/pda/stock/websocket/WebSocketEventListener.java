package com.pda.stock.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pda.stock.dto.Body;
import com.pda.stock.dto.Header;
import com.pda.stock.dto.Input;
import com.pda.stock.dto.StockPriceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

@Component
public class WebSocketEventListener {
    private final SimpMessagingTemplate messagingTemplate;


    private ArrayList<String> sessionIds = new ArrayList<String>();

    private WebSocketSession webSession;
    // 웹 소켓 세션

    private final Map<String,String> socketData = new ConcurrentHashMap<>();
    // 종목코드, 주가





    @Autowired
    public WebSocketEventListener(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void connectSocket(String stockCode) throws ExecutionException, InterruptedException, IOException {
        StandardWebSocketClient client = new StandardWebSocketClient();

        webSession = client.execute(new TextWebSocketHandler() {
            @Override
            protected void handleTextMessage(WebSocketSession session, TextMessage message) {

                if(message.getPayload().contains("{")){
                    System.out.println("close stock market");
                }else{
                    String[] stockInfos=message.getPayload().split("\\|")[3].split("\\^");

                    socketData.put(stockInfos[0],stockInfos[2]);
                    messagingTemplate.convertAndSend("/"+stockInfos[0]+"/greetings", new StockPriceDto(socketData.get(stockInfos[0])));
                    System.out.println(stockInfos[0]+": "+stockInfos[2]);
                }

            }
        }, "ws://ops.koreainvestment.com:21000").get();


        // ObjectMapper를 사용하여 JSON 데이터를 문자열로 변환합니다.
        ObjectMapper objectMapper = new ObjectMapper();


        WebSocketMessage dumyMessage = new WebSocketMessage();

        Header header = new Header();
        header.appkey = "PSLYhipOcQOL96OZnW6tkVSh0MG9uTEoOlZz";
        header.appsecret="srH+UJc03CM+bqGUlDqDMyQxXuO   beovPyrlwiMGxVcQttFBkM4mpwIW1L2omkIXa19KzK8qg7+Lbhix6G5Q3njIXYH1REHy8wHfaQ1OW33I8dFfok4xuu1GorhU1FlYUeAW3MgKCXnPtBWIFrT7kKAX8HEfkYWAxt6X4SNcnDXOyMelFr4E=";
        header.custtype = "P";
        header.tr_type = "1";
        header.content_type = "utf-8";

        Body body = new Body();
        Input input = new Input();

        input.tr_id = "H0STCNT0";
        input.tr_key = stockCode;
        System.out.println("tr_key: "+input.tr_key
        );
        body.input=input;

        dumyMessage.header = header;
        dumyMessage.body = body;

        // JSON 객체를 문자열로 변환합니다.
        String jsonMessage = objectMapper.writeValueAsString(dumyMessage);

        webSession.sendMessage(new TextMessage(jsonMessage));
    }

    public void connectStock(String stockCode) throws IOException, ExecutionException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();

        WebSocketMessage dumyMessage = new WebSocketMessage();

        Header header = new Header();
        header.appkey = "PSLYhipOcQOL96OZnW6tkVSh0MG9uTEoOlZz";
        header.appsecret="srH+UJc03CM+bqGUlDqDMyQxXuObeovPyrlwiMGxVcQttFBkM4mpwIW1L2omkIXa19KzK8qg7+Lbhix6G5Q3njIXYH1REHy8wHfaQ1OW33I8dFfok4xuu1GorhU1FlYUeAW3MgKCXnPtBWIFrT7kKAX8HEfkYWAxt6X4SNcnDXOyMelFr4E=";
        header.custtype = "P";
        header.tr_type = "1";
        header.content_type = "utf-8";

        Body body = new Body();
        Input input = new Input();

        input.tr_id = "H0STCNT0";
        input.tr_key = stockCode;
        System.out.println("tr_key: "+input.tr_key
        );
        body.input=input;

        dumyMessage.header = header;
        dumyMessage.body = body;

        // JSON 객체를 문자열로 변환합니다.
        String jsonMessage = objectMapper.writeValueAsString(dumyMessage);
        webSession.sendMessage(new TextMessage(jsonMessage));

    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) throws IOException, ExecutionException, InterruptedException {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String sessionId = headerAccessor.getSessionId();

        String userName = sessionId; // 예시로 세션 ID를 사용자 이름으로 사용
        sessionIds.add(sessionId);
        System.out.println(sessionId+" connect");




    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) throws IOException {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        System.out.println("handleWebSocketDisconnectListener "+sessionId+" Success");

        System.out.println(sessionId+" disconnect");


    }

}

