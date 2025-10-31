package com.mesago.msauth.api.dto;

import com.mesago.msauth.domain.entities.Role;
import com.mesago.msauth.domain.entities.Worker;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class WorkerDetailPublic {
    private Long workerId;
    private String name;
    private Role role;

    public static  WorkerDetailPublic fromEntity(Worker worker) {
        return WorkerDetailPublic.builder()
                .workerId(worker.getId())
                .name(worker.getName())
                .role(worker.getRole()).build();
    }
}
