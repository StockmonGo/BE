package com.pda.stock.websocket;

import io.micrometer.observation.annotation.Observed;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        config.enableSimpleBroker("/topic","/queue","/content");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Observed
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/gs-guide-websocket");
    }

}
