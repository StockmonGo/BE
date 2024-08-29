package com.pda.core.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import static com.pda.core.exception.ExceptionMessage.OUT_OF_WORLD_RANGE;
import static com.pda.core.utils.WorldConstants.*;

@Getter
@RequiredArgsConstructor
public class GetWorldStockmonsRequestDto {

    @DecimalMin(value = MIN_LATITUDE_STRING, message = OUT_OF_WORLD_RANGE)
    @DecimalMax(value = MAX_LATITUDE_STRING, message = OUT_OF_WORLD_RANGE)
    private final BigDecimal latitude;

    @DecimalMin(value = MIN_LONGITUDE_STRING, message = OUT_OF_WORLD_RANGE)
    @DecimalMax(value = MAX_LONGITUDE_STRING, message = OUT_OF_WORLD_RANGE)
    private final BigDecimal longitude;

}
