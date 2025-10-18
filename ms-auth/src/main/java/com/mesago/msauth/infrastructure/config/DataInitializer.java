package com.mesago.msauth.infrastructure.config;

import com.mesago.msauth.domain.entities.Role;
import com.mesago.msauth.domain.entities.User;
import com.mesago.msauth.domain.entities.Worker;
import com.mesago.msauth.domain.enums.RoleName;
import com.mesago.msauth.domain.repositories.RoleRepository;
import com.mesago.msauth.domain.repositories.UserRepository;
import com.mesago.msauth.domain.repositories.WorkerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final WorkerRepository workerRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository, WorkerRepository workerRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.workerRepository = workerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        if (roleRepository.count() == 0) {
            Arrays.stream(RoleName.values()).forEach(roleName -> {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
            });
            System.out.println(">>> Roles por defecto creados.");
        }

        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.findByName(RoleName.ADMIN)
                    .orElseThrow(() -> new RuntimeException("Rol ADMIN no encontrado"));

            Worker adminWorker = new Worker();
            adminWorker.setDni("00000000");
            adminWorker.setEmail("admin@mesago.com");
            adminWorker.setName("Administrador del Sistema");
            adminWorker.setStatus("ACTIVO");
            adminWorker.setRole(adminRole);
            workerRepository.save(adminWorker);

            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setEstado("ACTIVO");
            adminUser.setWorker(adminWorker);
            userRepository.save(adminUser);
            System.out.println(">>> Usuario Administrador por defecto creado: user=admin, pass=admin123");
        }
    }
}
