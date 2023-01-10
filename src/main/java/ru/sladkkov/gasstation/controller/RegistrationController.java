package ru.sladkkov.gasstation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sladkkov.gasstation.dto.request.RegisterRequest;
import ru.sladkkov.gasstation.service.RegistrationService;

import javax.management.relation.RoleNotFoundException;

@RestController
@RequestMapping(value = "api/v1")
@Tag(name = "Registration controller", description = "Регистрация пользователя")

public class RegistrationController {
    @Parameter(description = "Сервис для регистрации. " +
            "Содержит всю логику для операций регистрации.")
    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }


    @Operation(
            summary = "Регистрация.",
            description = "Позволяет зарегистрировать пользователя"
    )
    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody RegisterRequest registerRequest) throws RoleNotFoundException {
        registrationService.registration(registerRequest);
        return ResponseEntity.ok("Вы успешно зарегистрировались");
    }
}
