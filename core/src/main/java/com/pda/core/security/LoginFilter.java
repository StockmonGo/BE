package com.pda.core.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pda.core.dto.CustomUserDetailDto;
import com.pda.core.dto.SignInRequestDto;
import com.pda.core.exception.RequestBodyException;
import com.pda.core.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JwtUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil){
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/core/users/signin");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            SignInRequestDto signInRequestDto = objectMapper.readValue(request.getReader(), SignInRequestDto.class);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(signInRequestDto.getNickname(), signInRequestDto.getPassword());

            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RequestBodyException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {

        CustomUserDetailDto customUserDetailDto = (CustomUserDetailDto) authentication.getPrincipal();

        Long id = customUserDetailDto.getId();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();
        String token = jwtUtil.createJwt(id, role, 24 * 60 * 60 * 1000L);

        String jsonData = "{" +
                "\"result\":\"success\"," +
                "\"message\":" +
                "\"로그인 완료\", " +
                "\"data\" : {" +
                    "\"accessToken\" : \"" + token +
                "\"}," +
                "\"timestamp\":\"" +
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(new Date()) +
                "\"}";

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Authorization", "Bearer " + token);
        response.getWriter().write(jsonData);
    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        //로그인 실패시 401 응답 코드 반환
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String jsonData = "{" +
                "\"result\":\"fail\"," +
                "\"message\":\"" +
                (failed.getCause() == null ? "비밀번호 불일치" : "아이디 불일치") +
                "\"," +
                "\"timestamp\":\"" +
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(new Date()) +
                "\"}";

        response.getWriter().write(jsonData);
    }
}
