package com.pda.core.dto.traveler.account;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetTravelerStockListResponseDto {
    private List<TravelerStockDto> stocks;
}
