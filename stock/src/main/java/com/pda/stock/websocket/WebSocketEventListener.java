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

    private WebSocketSession webSession;
    // 웹 소켓 세션

    @Value(value = "${stock.app.key}")
    private String appKey;

    @Value(value = "${stock.app.sercret}")
    private String appSecret;
    // 종목코드, 유저세션


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

                }else{
                    String[] stockInfos=message.getPayload().split("\\|")[3].split("\\^");
                    System.out.println("value: "+stockInfos[0]);
                    socketData.put(stockInfos[0],stockInfos[1]);

                }

                System.out.println("Received message: " + message.getPayload().contains("{"));

            }
        }, "ws://ops.koreainvestment.com:21000").get();


        // ObjectMapper를 사용하여 JSON 데이터를 문자열로 변환합니다.
        ObjectMapper objectMapper = new ObjectMapper();


        WebSocketMessage dumyMessage = new WebSocketMessage();

        Header header = new Header();
        header.appkey = appKey;
        header.appsecret=appSecret;
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

    }

    public void connectStock(String stockCode) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        WebSocketMessage dumyMessage = new WebSocketMessage();

        Header header = new Header();
        header.appkey = appKey;
        header.appsecret = appSecret;
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


        System.out.println(headerAccessor.getSessionId());
        if(num==0){
            sessionIdToUserMap.put( "/topic/greetings",sessionId);
            connectSocket("024110");
            connectStock("024110");
            num++;
            System.out.println("num=0");

        }else{
            sessionIdToUserMap.put("/content/chart",sessionId);
            connectStock("105560");
            System.out.println("num=1");
        }
        System.out.println("handleWebSocketConnectListener"+sessionId);


        System.out.println("New WebSocket connection established. Session ID: " + sessionId);

    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) throws IOException {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        System.out.println("handleWebSocketDisconnectListener"+sessionId);

        sessionIdToUserMap.remove(sessionId);
        socketData.remove(sessionId);
        num--;
    }

    @Scheduled(fixedRate = 500)
    public void send(){
        int num=0;
        synchronized(sessionIdToUserMap){
            for (String stockCode : sessionIdToUserMap.keySet()) {
                String session = sessionIdToUserMap.get(stockCode);


                System.out.println("send: "+stockCode);
                if(num==0){
                    System.out.println("socketData: "+socketData.get("105560"));
                    num++;
                }else{
                    System.out.println("socketData: "+socketData.get("024110"));
                    num--;
                }



                messagingTemplate.convertAndSend(stockCode, new StockPriceDto(session));
            }
        }
    }


}
