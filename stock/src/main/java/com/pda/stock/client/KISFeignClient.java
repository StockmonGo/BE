package com.pda.stock.client;

import com.pda.stock.dto.StockChartDto;
import com.pda.commons.dto.StockInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "test", url = "https://openapi.koreainvestment.com:9443")
public interface KISFeignClient {


    default StockInfoDto getStockInfo(String code,
                                                      String token,
                                                      String appKey,
                                                      String appSecret) {
        return getStockInfo(code, "J", token, appKey, appSecret, "FHKST01010100", "P", "application/json;charset=utf-8");

    }

    @GetMapping("/uapi/domestic-stock/v1/quotations/inquire-price")
    StockInfoDto getStockInfo(
            @RequestParam("FID_INPUT_ISCD") String code,
            @RequestParam("FID_COND_MRKT_DIV_CODE") String type,
            @RequestHeader("Authorization") String token,
            @RequestHeader("appkey") String appKey,
            @RequestHeader("appsecret") String appSecret,
            @RequestHeader("tr_id") String trId,
            @RequestHeader("custtype") String custType,
            @RequestHeader("Content-Type") String contentType);

    default StockChartDto getMonthChart(String code,
                                                        String token,
                                                        String appKey,
                                                        String appSecret) {
        return getMonthChart(code, "J", "M", "0", token, appKey, appSecret, "FHKST01010400");
    }

    @GetMapping("/uapi/domestic-stock/v1/quotations/inquire-daily-itemchartprice")
    StockChartDto getMonthChart(@RequestParam("FID_INPUT_ISCD") String code,
                                                 @RequestParam("FID_COND_MRKT_DIV_CODE") String type,
                                                 @RequestParam("FID_PERIOD_DIV_CODE") String period,
                                                 @RequestParam("FID_ORG_ADJ_PRC") String update,
                                                 @RequestHeader("Authorization") String token,
                                                 @RequestHeader("appkey") String appKey,
                                                 @RequestHeader("appsecret") String appSecret,
                                                 @RequestHeader("tr_id") String trId);
}
