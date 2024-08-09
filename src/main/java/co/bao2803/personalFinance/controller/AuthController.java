package co.bao2803.personalFinance.controller;

import co.bao2803.personalFinance.dto.ResponseDto;
import co.bao2803.personalFinance.service.JwtService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    @PostMapping("/login")
    @SecurityRequirement(name = "basicAuth")
    public ResponseDto<String> login(
            @Autowired final Authentication authentication
    ) {
        final String token = jwtService.generateJwt(authentication);
        return ResponseDto.success(token);
    }

    @GetMapping("/logout")
    @SecurityRequirement(name = "bearerJwt")
    public ResponseDto<ResponseDto.EmptyResponse> logout(
            @Autowired final JwtAuthenticationToken token
    ) {
        jwtService.invalidateJwt(token);
        return ResponseDto.success();
    }

    @GetMapping("/test")
    @SecurityRequirement(name = "bearerJwt")
    public String test() {
        return "Hello, World";
    }
}
