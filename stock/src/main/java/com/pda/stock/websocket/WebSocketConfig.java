package com.pda.stock.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private CustomHandshakeHandler customHandshakeHandler;

    public  WebSocketConfig(CustomHandshakeHandler customHandshakeHandler){
        this.customHandshakeHandler = customHandshakeHandler;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        /**
         * 이 코드는 /topic으로 시작하는 주소를 가진 메시지에 대해 간단한 메모리 기반의 메시지 브로커를 활성화 한다. 이 브로커는 구독자에게 메시지를 전달한다.
         * prefix가 붙은 메시지가 송신되었을때 내장 브로커가 처리
         */
        config.enableSimpleBroker(
                "/008770","/034950",
                "/000660","/005930","/066570","/005380","/097950","/003230","/090430","/041830","/017890","/004370","/005180","/000270","/001460",
                "/086450","/068270","/009290","/293480",
                "/004170","/139480","/031430","/339770","/004360","/121440","/071840","/007070",
                "/215200","/067280",
                "/051600","/000720","/028260",
                "/035420","/181710","/030200","/035720","/259960","/067160","/095660","/053800","/143240","/018260",
                "/035900","/263720","/041510","/034120","/035760",
                "/105560","/024110","/086790","/316140","/016360","/031430","/030610","/005940","/006800","/088350","/005830","/071050"




        );

        System.out.println("WebSocketConfig-configureMessageBroker");

        /**
         *  메시지 핸들러로 라우팅 되는 prefix
         */
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        System.out.println("WebSocketConfig-registerStompEndpoints");

        registry
                .addEndpoint("/api/stock/gs-guide-websocket")
                .setAllowedOrigins("*")
                .addInterceptors(customHandshakeHandler);
    }

}