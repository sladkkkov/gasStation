package ru.sladkkov.gasstation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableWebSecurity
@EnableJpaRepositories
public class GasStationApplication {

    public static void main(String[] args) {
        SpringApplication.run(GasStationApplication.class, args);
    }

}
