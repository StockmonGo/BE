package com.pda.core.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pda.core.entity.World;
import com.pda.core.service.RedisService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.Access;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


// todo:  test 용 API 나중에 지우기
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class SwaggerTestController {

    private final RedisTemplate<String, Object> redisTemplate;
    private final GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer;
    private final ObjectMapper objectMapper;
    private final RedisService redisService;

    @GetMapping
    @Operation(summary = "테스트 API 입니다.")
    public String test() {
        return "test";
    }

    @PostMapping("/set")
    public String setValue(@RequestParam("key") String key, @RequestBody World world) throws JsonProcessingException {
        redisService.pushToList(key, world);
        return "값 설정";
    }

    @GetMapping("/get")
    public List<Object> getValue(@RequestParam("key") String key) {
        return redisService.getList(key);
    }
}
