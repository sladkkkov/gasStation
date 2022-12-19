package ru.sladkkov.gasstation.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sladkkov.gasstation.dto.request.RegisterRequest;
import ru.sladkkov.gasstation.exception.UserAlreadyCreatedException;
import ru.sladkkov.gasstation.model.RoleEnum;
import ru.sladkkov.gasstation.model.User;
import ru.sladkkov.gasstation.repository.RoleRepository;
import ru.sladkkov.gasstation.repository.UserRepository;


import javax.management.relation.RoleNotFoundException;
import java.util.List;

@Service
@Slf4j
public class RegistrationService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public RegistrationService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void registration(RegisterRequest registerRequest) throws RoleNotFoundException {
        log.info("User with username: " + registerRequest.getUsername() + " start registration");
        User user = new User(registerRequest.getUsername(), encodePassword(registerRequest), registerRequest.getFirstName(), registerRequest.getLastName());

        if (userRepository.findByUsername(registerRequest.getUsername()).isEmpty()) {
            setUserRole(user);
            userRepository.save(user);
            log.info("User: " + registerRequest.getUsername() + " successfully created");

        } else {
            log.error("User: " + registerRequest.getUsername() + " is already created");
            throw new UserAlreadyCreatedException("Пользователь с таким логином: " + registerRequest.getUsername() + " уже существует");
        }
    }

    private String encodePassword(RegisterRequest registerRequest) {
        return bCryptPasswordEncoder.encode(registerRequest.getPassword());
    }

    private void setUserRole(User user) throws RoleNotFoundException {
        user.setRoles(List.of(roleRepository.findByName(RoleEnum.USER).orElseThrow(() -> new RoleNotFoundException("Такой роли не существует"))));
        log.info("Successfully set role to User");
    }
}
