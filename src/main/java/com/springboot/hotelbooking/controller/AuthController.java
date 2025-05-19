package com.springboot.hotelbooking.controller;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.hotelbooking.dto.LoginDto;
import com.springboot.hotelbooking.dto.LoginResponseDto;
import com.springboot.hotelbooking.dto.SignUpDto;
import com.springboot.hotelbooking.dto.UserDto;
import com.springboot.hotelbooking.security.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
	
	private final AuthService authService;
	   
	@PostMapping("/signup")
	public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto  ){
		return new ResponseEntity<>(authService.signUp(signUpDto),HttpStatus.CREATED);
	}
	
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        String[] tokens = authService.login(loginDto);

        Cookie cookie = new Cookie("refreshToken", tokens[1]);
        cookie.setHttpOnly(true);

        httpServletResponse.addCookie(cookie);
        return ResponseEntity.ok(new LoginResponseDto(tokens[0]));

    	
    }
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request) {
        String refreshToken = Arrays.stream(request.getCookies()).
                filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found inside the Cookies"));

        String accessToken = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(new LoginResponseDto(accessToken));
    }
}
