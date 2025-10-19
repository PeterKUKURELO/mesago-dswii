package com.mesago.msauth.api.dto;

import com.mesago.msauth.domain.enums.RoleName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateWorkerRequest {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @NotBlank(message = "El email no puede estar vacío")
    private String email;

    @NotBlank(message = "El estado no puede estar vacío")
    private String status;

    @NotNull(message = "El rol no puede ser nulo")
    private RoleName roleName;

    private String telefono;
    private String shift;


}
