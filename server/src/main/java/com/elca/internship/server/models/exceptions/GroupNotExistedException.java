package com.elca.internship.server.models.exceptions;

import lombok.Data;

@Data
public class GroupNotExistedException extends RuntimeException{
    private Long groupId;
    public GroupNotExistedException(Long groupId){
        super(String.format("Group %s is not existed!", groupId));
        this.groupId = groupId;
    }
}
