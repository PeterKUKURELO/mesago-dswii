package com.mesago.msauth.application.services;

import com.mesago.msauth.api.dto.AuthResponse;
import com.mesago.msauth.api.dto.LoginRequest;
import com.mesago.msauth.api.dto.RegisterRequest;
import com.mesago.msauth.domain.entities.Role;
import com.mesago.msauth.domain.entities.User;
import com.mesago.msauth.domain.entities.Worker;
import com.mesago.msauth.domain.repositories.RoleRepository;
import com.mesago.msauth.domain.repositories.UserRepository;
import com.mesago.msauth.domain.repositories.WorkerRepository;
import com.mesago.msauth.infrastructure.security.jwt.JwtUtils;
import jakarta.transaction.Transactional;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final WorkerRepository workerRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    public AuthServiceImpl(UserRepository userRepository, WorkerRepository workerRepository,
                           RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.workerRepository = workerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    @Transactional
    public void register(RegisterRequest registerRequest) {
        if(userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("El username ya existe");
        }
        if (workerRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Error: El email ya está registrado.");
        }

        Role userRole = roleRepository.findByName(registerRequest.getRoleName())
                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));

        Worker worker = new Worker();
        worker.setDni(registerRequest.getDni());
        worker.setEmail(registerRequest.getEmail());
        worker.setName(registerRequest.getName());
        worker.setTelefono(registerRequest.getTelefono());
        worker.setRole(userRole);
        worker.setStatus("ACTIVO");
        Worker savedWorker = workerRepository.save(worker);

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEstado("ACTIVO");
        user.setWorker(savedWorker);
        userRepository.save(user);
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtUtils.generateToken(user);

        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse(null);

        return new AuthResponse(accessToken, user.getUsername(), role);
    }


}
