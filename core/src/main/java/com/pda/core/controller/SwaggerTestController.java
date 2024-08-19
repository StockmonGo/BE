package com.pda.core.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// todo:  test 용 API 나중에 지우기
@RestController
@RequestMapping("/api/test")
public class SwaggerTestController {

    @GetMapping
    @Operation(summary = "테스트 API 입니다.")
    public String test() {
        return "test";
    }
}
