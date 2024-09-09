package com.pda.stock.service;

import com.pda.stock.websocket.WebSocketEventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Component
public class MyApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

    private WebSocketEventListener webSocketEventListener;

    public MyApplicationListener( WebSocketEventListener webSocketEventListener){
        this.webSocketEventListener = webSocketEventListener;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // 애플리케이션이 준비된 후 한 번만 실행되는 코드
        try {
            webSocketEventListener.connectSocket("316140");
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("ApplicationReadyEvent: 애플리케이션 준비 완료 시 한 번 실행");
    }
}
