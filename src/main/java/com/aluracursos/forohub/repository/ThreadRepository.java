package com.aluracursos.forohub.repository;

import com.aluracursos.forohub.models.Thread;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadRepository extends JpaRepository<Thread,Long> {
}
