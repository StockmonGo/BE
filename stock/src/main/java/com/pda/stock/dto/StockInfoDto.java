package com.pda.stock.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StockInfoDto {

    @JsonProperty("output")
    private Output output;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Output {

        @JsonProperty("hts_avls")
        private String totalPrice;

        @JsonProperty("stck_prpr")
        private String currentPrice;
    }

}
