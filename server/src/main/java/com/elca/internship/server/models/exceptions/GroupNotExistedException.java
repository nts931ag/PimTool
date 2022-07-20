package com.elca.internship.server.models.exceptions;

public class GroupNotExistedException extends Exception{
    public GroupNotExistedException(Long groupId){
        super(String.format("Group %s is already existed.", groupId));

    }
}
