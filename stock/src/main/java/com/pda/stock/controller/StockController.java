package com.pda.stock.controller;

import com.pda.stock.dto.StockPriceDto;
import com.pda.stock.service.KISService;
import com.pda.stock.websocket.WebSocketEventListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@RestController
public class StockController {
    private final WebSocketEventListener webSocketEventListener;
    private final KISService kisService;

    public StockController(WebSocketEventListener webSocketEventListener,KISService kisService){
        this.webSocketEventListener = webSocketEventListener;
        this.kisService = kisService;
    }

    @MessageMapping("/{id}")
    @SendTo("/{id}/greetings") // "topic/greetings"를 핸들러에서 처리를 마친 메시지를 구독하고 있는 모든 구독자에게 메시지 푸시
    public StockPriceDto greeting(@DestinationVariable String id) throws Exception {
        System.out.println("stock controller: " + id);
        SimpleDateFormat sdf = new SimpleDateFormat("HH", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        int timeStamp=Integer.parseInt(sdf.format(new Date()));
        System.out.println(timeStamp);

        if(timeStamp<9 || timeStamp>=15){
            return new StockPriceDto(HtmlUtils.htmlEscape(""+kisService.getStockClosedPrice(id)));
        }

        webSocketEventListener.connectStock(id);
        return new StockPriceDto(HtmlUtils.htmlEscape(""+kisService.getStockClosedPrice(id)));
    }
}
