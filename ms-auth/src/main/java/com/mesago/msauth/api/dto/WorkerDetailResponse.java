package com.mesago.msauth.api.dto;

import com.mesago.msauth.domain.entities.Worker;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class WorkerDetailResponse {

    private Long workerId;
    private String dni;
    private String name;
    private String email;
    private String telefono;
    private String status;
    private String shift;
    private String role;
    private String username;

    public static WorkerDetailResponse fromEntity(Worker worker) {
        return WorkerDetailResponse.builder()
                .workerId(worker.getId())
                .dni(worker.getDni())
                .name(worker.getName())
                .email(worker.getEmail())
                .telefono(worker.getTelefono())
                .status(worker.getStatus())
                .shift(worker.getShift())
                .role(worker.getRole().getName().name())
                .username(worker.getUser().getUsername())
                .build();
    }

}
