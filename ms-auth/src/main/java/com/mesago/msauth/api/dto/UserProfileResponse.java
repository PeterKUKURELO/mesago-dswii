package com.mesago.msauth.api.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserProfileResponse {

    private Long userId;
    private String username;
    private String workerName;
    private String workerEmail;
    private String workerPhone;
    private String role;
}
