package ru.sladkkov.gasstation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.sladkkov.gasstation.model.Role;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class JwtResponse {

    private Long id;

    private String token;

    private String username;

    private List<Role> roles;
}
