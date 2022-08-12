package com.elca.internship.server.models.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;


@Table(name = "team", schema = "pim_tool_db_migration")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Group extends BaseEntity {
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "leader_id", referencedColumnName = "id")
    private Employee groupLeaderId;
    @OneToMany(mappedBy = "group")
    private Set<Project> projectSet = new HashSet<>();
/*    public Group(long id, long groupLeaderId, int version){
        super(id,version);
//        this.groupLeaderId = groupLeaderId;
    }*/

    public Group(Long id, Integer version, Employee groupLeaderId, Set<Project> projectSet) {
        super(id, version);
        this.groupLeaderId = groupLeaderId;
        this.projectSet = projectSet;
    }

    public void addProjectToGroup(Project project){
        if(projectSet == null){
            projectSet = new LinkedHashSet<>();
        }
        projectSet.add(project);
    }
}
