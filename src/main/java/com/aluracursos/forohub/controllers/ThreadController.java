package com.aluracursos.forohub.controllers;

import com.aluracursos.forohub.dto.CreateReplyData;
import com.aluracursos.forohub.dto.CreateThreadData;
import com.aluracursos.forohub.dto.FullThreadData;
import com.aluracursos.forohub.dto.ThreadData;
import com.aluracursos.forohub.services.ThreadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/threads")
public class ThreadController {

    @Autowired
    private ThreadService threadService;

    // Get all threads
    @GetMapping
    public ResponseEntity<?> getThreads(@PageableDefault(size = 10)Pageable pageable){
        var pageData=threadService.getThreads(pageable).map(ThreadData::new);
        return ResponseEntity.ok(pageData);
    }

    // Create a new thread
    @PostMapping
    public ResponseEntity<?> createThread(@RequestBody @Valid CreateThreadData createThreadData){
        var threadData=threadService.createThread(createThreadData);
        return ResponseEntity.ok(threadData);
    }

    // Get a single thread with all its content
    @GetMapping("/{id}")
    public ResponseEntity<FullThreadData> getThread(@PageableDefault(size = 10) Pageable pageable,
                                                    @PathVariable @Valid Long id){
        return ResponseEntity.ok(threadService.getThread(id,pageable));
    }
    // Reply to a thread
    @PostMapping("/{id}")
    public ResponseEntity<?> replyThread(@PathVariable Long id,
                                                      @RequestBody @Valid CreateReplyData createReplyData){
        return ResponseEntity.ok(threadService.replyThread(id,createReplyData));
    }

    // Edit a thread
    @PutMapping("/{id}")
    public ResponseEntity<?> editThread(@PathVariable @Valid Long id,
                                                 @RequestBody CreateThreadData createThreadData){
        return ResponseEntity.ok(threadService.updateThread(id,createThreadData));
    }

    // Mark a thread as closed
    @PutMapping("/{id}/close")
    public ResponseEntity<?> closeThread(@PathVariable @Valid Long id){
        return ResponseEntity.ok(threadService.closeThread(id));
    }

    // Mark a thread as deleted
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteThread(@PathVariable @Valid Long id){
        return ResponseEntity.ok(threadService.deleteThread(id));
    }

}
