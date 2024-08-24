package com.pda.core.dto;


import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.pda.core.exception.ExceptionMessage.OUT_OF_WORLD_RANGE;

@Getter
@RequiredArgsConstructor
public class GetWorldStockmonsRequestDto {
    @DecimalMin(value = "37.42928001105901", message = OUT_OF_WORLD_RANGE)
    @DecimalMax(value = "37.73892900008836", message = OUT_OF_WORLD_RANGE)
    private final int latitude;

    @DecimalMax(value = "127.21925741327027", message =  OUT_OF_WORLD_RANGE)
    @DecimalMin(value = "126.76605328501563", message =  OUT_OF_WORLD_RANGE)
    private final double longitude;

}
