package com.pda.core.controller;


import com.pda.commons.dto.SuccessResponse;
import com.pda.core.dto.GetWorldStockmonsRequestDto;
import com.pda.core.dto.GetWorldStockmonsResponseDto;
import com.pda.core.dto.WorldStockmonDto;
import com.pda.core.entity.StockTower;
import com.pda.core.entity.Stockmon;
import com.pda.core.entity.World;
import com.pda.core.service.StockService;
import com.pda.core.service.StockTowerService;
import com.pda.core.service.StockmonService;
import com.pda.core.service.WorldService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/maps")
public class WorldController {

    private final WorldService worldService;
    private final StockTowerService stockTowerService;
    private final StockmonService stockmonService;

    @PostMapping("/stockmons")
    @Operation(summary = "주변 스톡몬, 스톡타워 조회")
    public ResponseEntity<SuccessResponse<GetWorldStockmonsResponseDto>> getMapStockmons(@Valid @RequestBody GetWorldStockmonsRequestDto getWorldStockmonsRequestDto) {
        List<World> worlds = worldService.getNearWorlds();
        List<StockTower> stockTowers = stockTowerService.getNearStockTowers();
        List<WorldStockmonDto> worldStockmonDtos = new ArrayList<>();

        for(World world : worlds) {
            Stockmon stockmon = stockmonService.getStockmon(world.getStockmonId());
            WorldStockmonDto worldStockmonDto = new WorldStockmonDto(world.getId(), stockmon.getImgUrl(), world.getLatitude(), world.getLongitude());
            worldStockmonDtos.add(worldStockmonDto);
        }

        return ResponseEntity.ok(SuccessResponse
                .<GetWorldStockmonsResponseDto>builder()
                        .data(new GetWorldStockmonsResponseDto(worldStockmonDtos, stockTowers))
                        .message("성공")
                        .timestamp(new Date())
                .build());
    }
}
