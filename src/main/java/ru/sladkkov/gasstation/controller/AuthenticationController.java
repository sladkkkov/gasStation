package ru.sladkkov.gasstation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sladkkov.gasstation.service.AuthenticationService;
import ru.sladkkov.gasstation.dto.request.LoginRequest;
import ru.sladkkov.gasstation.dto.response.JwtResponse;

@RestController
@RequestMapping(value = "api/v1")
@Tag(name = "Authentication controller", description = "Аутентификация пользователя")
public class AuthenticationController {

    @Parameter(description = "Сервис для авторизации." +
            "Содержит всю логику для операций авторизации.")
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Operation(
            summary = "Авторизация.",
            description = "Позволяет авторизировать пользователя"
    )
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/admin/1")
    public ResponseEntity<String> getString() {
        return ResponseEntity.ok("Проверка преАвторайза ");
    }
}
