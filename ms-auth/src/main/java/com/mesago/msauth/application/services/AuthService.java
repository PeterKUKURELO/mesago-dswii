package com.mesago.msauth.application.services;

import com.mesago.msauth.api.dto.AuthResponse;
import com.mesago.msauth.api.dto.LoginRequest;
import com.mesago.msauth.api.dto.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest registerRequest);

    AuthResponse login(LoginRequest loginRequest);

}
