package me.seoop.newgogidang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NewgogidangApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewgogidangApplication.class, args);
    }

}
