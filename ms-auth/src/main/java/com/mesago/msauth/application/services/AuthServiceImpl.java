package com.mesago.msauth.application.services;

import com.mesago.msauth.api.dto.*;
import com.mesago.msauth.domain.entities.Role;
import com.mesago.msauth.domain.entities.User;
import com.mesago.msauth.domain.entities.Worker;
import com.mesago.msauth.domain.repositories.RoleRepository;
import com.mesago.msauth.domain.repositories.UserRepository;
import com.mesago.msauth.domain.repositories.WorkerRepository;
import com.mesago.msauth.infrastructure.security.jwt.JwtUtils;
import jakarta.transaction.Transactional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.mesago.msauth.api.exception.DuplicateResourceException;


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
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new DuplicateResourceException("El nombre de usuario '" + registerRequest.getUsername() + "' ya está en uso.");
        }
        if (workerRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new DuplicateResourceException("El email '" + registerRequest.getEmail() + "' ya está registrado.");
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

    @Override
    public UserProfileResponse getMyProfile(UserDetails currentUser) {

        User user = (User) currentUser;

        String role = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse(null);

        return UserProfileResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .workerName(user.getWorker().getName())
                .workerEmail(user.getWorker().getEmail())
                .role(role)
                .build();
    }


    @Override
    public List<WorkerDetailResponse> getAllWorkers() {
        return workerRepository.findAll()
                .stream()
                .map(WorkerDetailResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public WorkerDetailResponse updateWorker(Long workerId, UpdateWorkerRequest request) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado con ID: " + workerId));

        Role newRole = roleRepository.findByName(request.getRoleName())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + request.getRoleName()));

        worker.setName(request.getName());
        worker.setEmail(request.getEmail());
        worker.setStatus(request.getStatus());
        worker.setTelefono(request.getTelefono());
        worker.setShift(request.getShift());
        worker.setRole(newRole);

        User user = worker.getUser();
        user.setEstado(request.getStatus());

        Worker updatedWorker = workerRepository.save(worker);
        userRepository.save(user);

        return WorkerDetailResponse.fromEntity(updatedWorker);
    }


    @Override
    @Transactional
    public void deleteWorker(Long workerId) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado con ID: " + workerId));


        worker.setStatus("INACTIVO");

        User user = worker.getUser();
        user.setEstado("INACTIVO");

        workerRepository.save(worker);
        userRepository.save(user);
    }

}
