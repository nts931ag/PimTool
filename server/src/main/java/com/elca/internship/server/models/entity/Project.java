package com.elca.internship.server.models.entity;

import com.elca.internship.server.models.Status;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Table(name = "project", schema = "pim_tool_db_migration")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Project extends BaseEntity{
//    @Column(name = "group_id")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Group group;
    @Column(name = "project_number")
    private Integer projectNumber;
    @Column(name = "name")
    private String name;
    @Column(name = "customer")
    private String customer;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectEmployee> projectEmployee = new HashSet<>();

    public void addChildProjectEmployee(ProjectEmployee childProjectEmployee){
        if(projectEmployee == null){
            this.projectEmployee = new HashSet<>();
        }
        this.projectEmployee.add(childProjectEmployee);
    }

    public void removeChildProjectEmployee(ProjectEmployee childProjectEmployee){
        this.projectEmployee.remove(childProjectEmployee);

    }

    public Project(Long id, Integer version, Group group, Integer projectNumber, String name, String customer, Status status, LocalDate startDate, LocalDate endDate, Set<ProjectEmployee> projectEmployee) {
        super(id, version);
        this.group = group;
        this.projectNumber = projectNumber;
        this.name = name;
        this.customer = customer;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectEmployee = projectEmployee;
    }

}
