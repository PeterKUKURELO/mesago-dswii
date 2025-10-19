package com.mesago.msauth.application.services;

import com.mesago.msauth.api.dto.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface AuthService {
    void register(RegisterRequest registerRequest);

    AuthResponse login(LoginRequest loginRequest);

    UserProfileResponse getMyProfile(UserDetails currentUser);

    List<WorkerDetailResponse> getAllWorkers();

    WorkerDetailResponse updateWorker(Long workerId, UpdateWorkerRequest request);

    void deleteWorker(Long workerId);

}
