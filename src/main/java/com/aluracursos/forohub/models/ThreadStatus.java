package com.aluracursos.forohub.models;

import com.aluracursos.forohub.exceptions.InvalidThreadStatusException;

public enum ThreadStatus {

    ACTIVE("Active"),
    PAUSED("Paused"),
    CLOSED("Closed");

    private String threadStatus;

    ThreadStatus(String threadStatus){
        this.threadStatus=threadStatus;
    }
    public static ThreadStatus fromString(String threadStatus){
        for(ThreadStatus status : ThreadStatus.values()){
            if(status.threadStatus.equalsIgnoreCase(threadStatus))return status;
        }
        throw new InvalidThreadStatusException("Invalid thread status: "+threadStatus);
    }
}
