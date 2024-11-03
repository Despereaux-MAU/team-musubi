package com.musubi.teammusubi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TeamMusubiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamMusubiApplication.class, args);
    }

}
