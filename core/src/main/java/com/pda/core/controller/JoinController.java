package com.pda.core.controller;

import com.pda.core.dto.CustomUserDetails;
import com.pda.core.dto.JoinDTO;
import com.pda.core.dto.LoginDTO;
import com.pda.core.entity.Traveler;
import com.pda.core.service.CustomUserDetailsService;
import com.pda.core.service.JoinService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JoinController {

    private final JoinService joinService;
    private final CustomUserDetailsService customUserDetailsService;

    public JoinController(JoinService joinService, CustomUserDetailsService customUserDetailsService){
        this.joinService = joinService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/api/users/join")
    public String joinProcess(JoinDTO joinDTO){
        joinService.joinProcess(joinDTO);
        return "OK";
    }

    @PostMapping("/api/users/signin")
    public String loginProcess(LoginDTO loginDTO) {
        customUserDetailsService.loadUserByUsername(loginDTO.getNickname());
        return "OK";
    }

}
