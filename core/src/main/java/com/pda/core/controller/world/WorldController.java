package com.pda.core.controller.world;


import com.pda.commons.dto.SuccessResponse;
import com.pda.core.dto.world.stockmon.GetWorldStockmonsRequestDto;
import com.pda.core.dto.world.stockmon.GetWorldStockmonsResponseDto;
import com.pda.core.dto.world.stockmon.WorldStockmonDto;
import com.pda.core.entity.world.StockTower;
import com.pda.core.entity.stockmon.Stockmon;
import com.pda.core.entity.world.World;
import com.pda.core.service.world.StockTowerService;
import com.pda.core.service.stockmon.StockmonService;
import com.pda.core.service.world.WorldService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "지도 관련 API 모음")
@RequestMapping("/api/core/maps")
public class WorldController {

    private final WorldService worldService;
    private final StockTowerService stockTowerService;
    private final StockmonService stockmonService;

    @PostMapping("/stockmons")
    @Operation(summary = "주변 스톡몬, 스톡타워 조회 API")
    public ResponseEntity<SuccessResponse<GetWorldStockmonsResponseDto>> getMapStockmons(@Valid @RequestBody GetWorldStockmonsRequestDto getWorldStockmonsRequestDto) {

        List<World> worlds = worldService.getNearWorlds(getWorldStockmonsRequestDto);
        List<StockTower> stockTowers = stockTowerService.getNearStockTowers(getWorldStockmonsRequestDto);
        List<WorldStockmonDto> worldStockmonDtos = new ArrayList<>();
        if(worlds != null) {
            for (World world : worlds) {
                Stockmon stockmon = stockmonService.getStockmon(world.getStockmonId());
                WorldStockmonDto worldStockmonDto = new WorldStockmonDto(world.getId(), stockmon.getId(),
                        world.getLatitude(), world.getLongitude());
                worldStockmonDtos.add(worldStockmonDto);
            }
        }

        return ResponseEntity.ok(SuccessResponse
                .<GetWorldStockmonsResponseDto>builder()
                        .data(new GetWorldStockmonsResponseDto(worldStockmonDtos, stockTowers))
                        .message("성공")
                        .timestamp(new Date())
                .build());
    }

}
