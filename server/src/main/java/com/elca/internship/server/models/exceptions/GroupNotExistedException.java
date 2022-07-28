package com.elca.internship.server.models.exceptions;

public class GroupNotExistedException extends RuntimeException{
    public GroupNotExistedException(Long groupId){
        super(String.format("Group %s is not existed!", groupId));

    }
}
