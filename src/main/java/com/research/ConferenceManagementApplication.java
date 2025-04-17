// package com.research;

// import io.github.cdimascio.dotenv.Dotenv;
// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;

// import io.github.cdimascio.dotenv.Dotenv;

// @SpringBootApplication
// public class ConferenceManagementApplication {
//     public static void main(String[] args) {
//         Dotenv dotenv = Dotenv.load();
//         System.setProperty("DATABASE_URL", dotenv.get("SUPABASE_DATABASE_URL"));
//         System.setProperty("DATABASE_USER", dotenv.get("SUPABASE_DATABASE_USER"));
//         System.setProperty("DATABASE_PASSWORD", dotenv.get("SUPABASE_DATABASE_PASSWORD"));
//         SpringApplication.run(ConferenceManagementApplication.class, args);
//     }
// }

package com.research;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConferenceManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConferenceManagementApplication.class, args);
    }
}