package com.pda.core.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pda.core.entity.World;
import com.pda.core.repository.RedisRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


// todo:  test 용 API 나중에 지우기
@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class SwaggerTestController {

    private final RedisRepository redisRepository;


    @GetMapping
    @Operation(summary = "테스트 API 입니다.")
    public String test() {
        return "test";
    }

    @PostMapping("/set")
    public String setValue() throws JsonProcessingException {
        List<World> list = new ArrayList<>();
        for(int i = 0; i < 100000; i++) {
            World world = new World(1L, 1.0, 1.0, 1L, false);
            list.add(world);
        }
        redisRepository.setToListAll("heeeul", list);
        return "값 설정";
    }

    @GetMapping("/get")
    public List<Object> getValue(@RequestParam("key") String key) {
        return redisRepository.getList(key);
    }

}
