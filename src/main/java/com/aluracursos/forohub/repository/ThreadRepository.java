package com.aluracursos.forohub.repository;

import com.aluracursos.forohub.models.Thread;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadRepository extends JpaRepository<Thread,Long> {

    Boolean existsByTitle(@NotBlank String title);

    Boolean existsByMessage(@NotBlank String message);
}
