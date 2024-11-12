package com.aluracursos.forohub.controllers;

import com.aluracursos.forohub.dto.CreateReplyData;
import com.aluracursos.forohub.dto.ReplyData;
import com.aluracursos.forohub.dto.SingleReplyData;
import com.aluracursos.forohub.services.ReplyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @PutMapping("/{id}")
    public ResponseEntity<SingleReplyData> editReply(@PathVariable @Valid Long id,
                                                     @RequestBody CreateReplyData createReplyData){
        return ResponseEntity.ok(replyService.editReply(id,createReplyData));
    }

}
