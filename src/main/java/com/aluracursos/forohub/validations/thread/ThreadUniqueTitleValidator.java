package com.aluracursos.forohub.validations.thread;

import com.aluracursos.forohub.dto.CreateThreadData;
import com.aluracursos.forohub.exceptions.InvalidThreadDataException;
import com.aluracursos.forohub.exceptions.InvalidUserRegisterDataException;
import com.aluracursos.forohub.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ThreadUniqueTitleValidator implements ThreadValidator{

    @Autowired
    private ThreadRepository threadRepository;

    @Override
    public void validate(CreateThreadData createThreadData) {
        var title=threadRepository.existsByTitle(createThreadData.title());
        if(title) throw new InvalidThreadDataException("title: "+createThreadData.title()+" already exists");
    }
}
