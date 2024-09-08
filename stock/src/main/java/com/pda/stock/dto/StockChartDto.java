package com.pda.stock.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StockChartDto {

    @JsonProperty("output")
    private Output[] output;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Output {
        @JsonProperty("stck_clpr")
        private String value;

        @JsonProperty("stck_bsop_date")
        private String date;

        @JsonProperty("stck_prdy_hgpr")
        private String closedPrice;
    }

}
