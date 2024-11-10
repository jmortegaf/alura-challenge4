package com.aluracursos.forohub.controllers;

import com.aluracursos.forohub.dto.CreateThreadData;
import com.aluracursos.forohub.dto.ThreadData;
import com.aluracursos.forohub.services.ThreadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/threads")
public class ThreadController {

    @Autowired
    private ThreadService threadService;

    @GetMapping
    public ResponseEntity<Page<ThreadData>> getThreads(@PageableDefault(size = 10)Pageable pageable){
        var pageData=threadService.getThreads(pageable).map(ThreadData::new);
        return ResponseEntity.ok(pageData);
    }

    @PostMapping
    public ResponseEntity<ThreadData> createThread(@RequestBody @Valid CreateThreadData createThreadData){
        var threadData=threadService.createThread(createThreadData);
        return ResponseEntity.ok(threadData);
    }


}
