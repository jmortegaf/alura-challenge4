package com.aluracursos.forohub.repository;

import com.aluracursos.forohub.models.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply,Long> {
    Page<Reply> findByThreadId(Long id, Pageable pageable);
}
