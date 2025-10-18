package com.mesago.msauth.domain.repositories;

import com.mesago.msauth.domain.entities.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {

    Optional<Worker> findByName(String name);

    Optional<Worker> findByEmail(String email);

    Optional<Worker> findByDni(String dni);

}
