package com.elca.internship.server.models.entity;

import lombok.Data;


@Data
public class Group extends BaseEntity {
    private long groupLeaderId;

    public Group(long id, long groupLeaderId, int version){
        super(id,version);
        this.groupLeaderId = groupLeaderId;
    }
}
