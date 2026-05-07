package com.example.essayfeedback;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

// import com.example.essayfeedback.auth.entity.User;
// import com.example.essayfeedback.auth.repo.AuthRepo;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.example.essayfeedback.admin.entity.User;
import com.example.essayfeedback.admin.repo.AuthRepo;

@SpringBootApplication
public class EssayFeedbackApplication {
    public static void main(String[] args) {
        SpringApplication.run(EssayFeedbackApplication.class, args);
    }
	@Bean
	public static CommandLineRunner seedAdmin(AuthRepo authRepo) {
		return args -> {
			if (authRepo.findByEmail("master_admin@hospital.com").isEmpty()) {
				User admin = new User();
				admin.setUsername("master_admin");
				admin.setEmail("master_admin@hospital.com");
				admin.setPassword(BCrypt.hashpw("admin123", BCrypt.gensalt()));
				admin.setRole("Admin");
				admin.authtrue();
				authRepo.save(admin);
				System.out.println("Admin user seeded: master_admin@hospital.com / admin123");
			}
		};
	}

}