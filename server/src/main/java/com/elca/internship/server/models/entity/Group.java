package com.elca.internship.server.models.entity;

import lombok.Data;


@Data
public class Group {
    private long id;
    private long groupLeaderId;
    private int version;
}
