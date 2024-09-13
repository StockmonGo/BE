package com.pda.core.dto.world.stockmon;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import static com.pda.core.service.world.WorldConstant.*;

@Getter
@RequiredArgsConstructor
public class GetWorldStockmonsRequestDto {
    public static final String  OUT_OF_WORLD_RANGE = "사용자 좌표 오류";

    @DecimalMin(value = MIN_LATITUDE_STRING, message = OUT_OF_WORLD_RANGE)
    @DecimalMax(value = MAX_LATITUDE_STRING, message = OUT_OF_WORLD_RANGE)
    private final BigDecimal latitude;

    @DecimalMin(value = MIN_LONGITUDE_STRING, message = OUT_OF_WORLD_RANGE)
    @DecimalMax(value = MAX_LONGITUDE_STRING, message = OUT_OF_WORLD_RANGE)
    private final BigDecimal longitude;

}
