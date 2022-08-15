package com.elca.internship.server.exceptions;

public class GroupWithoutGroupLeaderException extends RuntimeException{
    public String visaNotExisted;

    public GroupWithoutGroupLeaderException(String visaNotExisted){
        super(String.format("Visa of leader not existed: %s!", visaNotExisted));
        this.visaNotExisted = visaNotExisted;
    }
}
