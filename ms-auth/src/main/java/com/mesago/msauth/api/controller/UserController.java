package com.mesago.msauth.api.controller;

import com.mesago.msauth.api.dto.UpdateWorkerRequest;
import com.mesago.msauth.api.dto.UserProfileResponse;
import com.mesago.msauth.api.dto.WorkerDetailResponse;
import com.mesago.msauth.application.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getMyUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        UserProfileResponse profile = authService.getMyProfile(userDetails);
        return ResponseEntity.ok(new ApiResponse<>(true, "Perfil obtenido correctamente", profile));
    }

    @GetMapping("/workers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<WorkerDetailResponse>>> getAllWorkers() {
        List<WorkerDetailResponse> workers = authService.getAllWorkers();
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de trabajadores obtenida", workers));
    }

    @PutMapping("/workers/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<WorkerDetailResponse>> updateWorker(
            @PathVariable Long id,
            @Valid @RequestBody UpdateWorkerRequest request) {

        WorkerDetailResponse updated = authService.updateWorker(id, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Trabajador actualizado correctamente", updated));
    }

    @DeleteMapping("/workers/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteWorker(@PathVariable Long id) {
        authService.deleteWorker(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Trabajador eliminado correctamente", null));
    }
}