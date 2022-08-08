package com.elca.internship.server.models.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;


@Data
@Table(name = "team", schema = "pim_tool_db_migration")
@Entity
public class Group extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "leader_id", referencedColumnName = "id")
    private Employee groupLeaderId;
    @OneToMany(mappedBy = "group")
    private Set<Project> projectSet;
    public Group(long id, long groupLeaderId, int version){
        super(id,version);
//        this.groupLeaderId = groupLeaderId;
    }
}
