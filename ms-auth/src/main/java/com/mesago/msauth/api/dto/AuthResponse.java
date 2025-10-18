package com.mesago.msauth.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {

    private String accessToken;

    private String username;

    private String role;

}

