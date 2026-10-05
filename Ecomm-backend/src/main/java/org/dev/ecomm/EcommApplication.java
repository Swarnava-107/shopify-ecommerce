package org.dev.ecomm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication
public class EcommApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommApplication.class, args);
    }

}
