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
    public ResponseEntity<UserProfileResponse> getMyUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        UserProfileResponse profile = authService.getMyProfile(userDetails);
        return ResponseEntity.ok(profile);
    }


    @GetMapping("/workers")
    public ResponseEntity<List<WorkerDetailResponse>> getAllWorkers() {
        return ResponseEntity.ok(authService.getAllWorkers());

    }

    @PutMapping("/workers/{id}")
    public ResponseEntity<WorkerDetailResponse> updateWorker(@PathVariable Long id, @Valid @RequestBody UpdateWorkerRequest request) {
        return ResponseEntity.ok(authService.updateWorker(id, request));
    }

    @DeleteMapping("/workers/{id}")
    public ResponseEntity<Void> deleteWorker(@PathVariable Long id) {
        authService.deleteWorker(id);
        return ResponseEntity.noContent().build();
    }

}