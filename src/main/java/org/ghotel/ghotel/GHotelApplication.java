package org.ghotel.ghotel;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class GHotelApplication {

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Sofia"));
    }

    public static void main(String[] args) {
        SpringApplication.run(GHotelApplication.class, args);
    }

}
