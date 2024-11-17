package com.aluracursos.forohub.validations.thread;

import com.aluracursos.forohub.dto.CreateThreadData;
import com.aluracursos.forohub.exceptions.InvalidThreadDataException;
import com.aluracursos.forohub.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ThreadUniqueContentValidator implements ThreadValidator{

    @Autowired
    private ThreadRepository threadRepository;

    @Override
    public void validate(CreateThreadData createThreadData) {
        var content=threadRepository.existsByMessage(createThreadData.message());
        if(content)throw new InvalidThreadDataException("Thread content duplicated");
    }
}
