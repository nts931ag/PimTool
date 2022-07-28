package com.elca.internship.server.models.exceptions;

import java.util.List;

public class GroupLeaderNotExistedException extends RuntimeException{
    public String visaNotExisted;

    public GroupLeaderNotExistedException(String visaNotExisted){
        super(String.format("Visa of leader not existed: %s!", visaNotExisted));
        this.visaNotExisted = visaNotExisted;
    }
}
