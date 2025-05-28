package com.techtwist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    static {
        System.out.println("DEBUG: MONGO_HOST=" + System.getenv("MONGO_HOST"));
        System.out.println("DEBUG: MONGO_PORT=" + System.getenv("MONGO_PORT"));
        System.out.println("DEBUG: MONGO_USERNAME=" + System.getenv("MONGO_USERNAME"));
        System.out.println("DEBUG: MONGO_PASSWORD_IS_SET=" + (System.getenv("MONGO_PASSWORD") != null && !System.getenv("MONGO_PASSWORD").isEmpty())); // Don't print the actual password
        System.out.println("DEBUG:  MONGO_PASSWORD=" + System.getenv("MONGO_PASSWORD"));
    }
    public static void main(String[] args) {  
        SpringApplication.run(Application.class, args);
    }
}
