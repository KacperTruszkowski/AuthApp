package com.example.authapp.config;

import com.example.authapp.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@RequiredArgsConstructor
public class JsonObjectAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            UserDto authRequest = objectMapper.readValue(sb.toString(), UserDto.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    authRequest.getLogin(), authRequest.getPassword()
            );
            setDetails(request, token);
            return this.getAuthenticationManager().authenticate(token);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}