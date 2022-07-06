package com.elca.internship.server.models.entity;

import lombok.Data;

@Data
public class BaseEntity {
    protected long id;
    protected int version;
}
