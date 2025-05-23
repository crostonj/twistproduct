package com.techtwist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();

                // Set env vars for Spring to resolve ${...} in application.yaml
       // System.setProperty("MONGO_USERNAME", dotenv.get("MONGO_USERNAME"));
       // System.setProperty("MONGO_PASSWORD", dotenv.get("MONGO_PASSWORD"));
       // System.setProperty("MONGO_HOST", dotenv.get("MONGO_HOST"));
       // System.setProperty("MONGO_PORT", dotenv.get("MONGO_PORT"));

        
        SpringApplication.run(Application.class, args);
    }
}
