package com.pda.core.dto.stockmon.yard;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class YardStockmonResponseDto {

    private List<YardStockmonDto> stockmons;

}
