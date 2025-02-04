package com.pda.core.controller;

import com.pda.core.client.StockFeignClient;
import com.pda.core.repository.RedisRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// todo:  test 용 API 나중에 지우기
@Tag(name = "테스트 용")
@RestController
@RequestMapping("/api/core/test")
@RequiredArgsConstructor
public class SwaggerTestController {

    private final RedisRepository redisRepository;
    private final StockFeignClient stockFeignClient;

    @GetMapping("/feign")
    public ResponseEntity<String> feignTest() {
        return stockFeignClient.getTest();
    }

    @GetMapping
    @Operation(summary = "테스트 API")
    public String test() {
        return "test";
    }

    @GetMapping("/get")
    @Operation(summary = "redis 조회 API")
    public List<Object> getValue(@RequestParam("key") String key) {
        return redisRepository.getList(key);
    }

}
