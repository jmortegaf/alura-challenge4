package com.aluracursos.forohub.controllers;

import com.aluracursos.forohub.dto.CreateReplyData;
import com.aluracursos.forohub.dto.ReplyData;
import com.aluracursos.forohub.dto.SingleReplyData;
import com.aluracursos.forohub.services.ReplyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reply")
@SecurityRequirement(name = "bearer-key")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    //Reply to an existing reply by its id;
    @PostMapping("/{id}")
    public ResponseEntity<?> replyToReply(@PathVariable @Valid Long id,
                                          @RequestBody @Valid CreateReplyData createReplyData){
        return ResponseEntity.ok(replyService.replyToReply(id,createReplyData));
    }

    // edit a reply by its id
    @PutMapping("/{id}")
    public ResponseEntity<?> editReply(@PathVariable @Valid Long id,
                                                     @RequestBody CreateReplyData createReplyData){
        return ResponseEntity.ok(replyService.editReply(id,createReplyData));
    }

    // Get a single reply by its id
    @GetMapping("/{id}")
    public ResponseEntity<SingleReplyData> getReply(@PathVariable @Valid Long id){
        return ResponseEntity.ok(replyService.getReply(id));
    }

    // delete a reply (it just marks them as deleted so the content it never really erased)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReply(@PathVariable @Valid Long id){
        return ResponseEntity.ok(replyService.deleteReply(id));
    }


}
