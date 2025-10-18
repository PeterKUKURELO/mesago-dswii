package com.mesago.msauth.api.dto;

import com.mesago.msauth.domain.enums.RoleName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "El DNI no puede estar vacío")
    private String dni;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El formato del email no es válido")
    private String email;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    private String telefono;

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(min = 4, message = "El nombre de usuario debe tener al menos 4 caracteres")
    private String username;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 4, message = "La contraseña debe tener al menos 4 caracteres")
    private String password;

    @NotNull(message = "Se debe especificar un rol")
    private RoleName roleName;

}
