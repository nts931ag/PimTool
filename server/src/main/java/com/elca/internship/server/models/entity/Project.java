package com.elca.internship.server.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project", schema = "pim_tool_db_migration")
@Entity
public class Project extends BaseEntity{
//    @Column(name = "group_id")
    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Group groupId;
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

    @OneToMany(mappedBy = "project")
    Set<ProjectEmployee> projectEmployees;

    public Project(long id, long groupId, Integer projectNumber, String name, String customer, Status status, LocalDate startDate, LocalDate endDate, int version) {
        super(id, version);
//        this.groupId = groupId;
        this.projectNumber = projectNumber;
        this.name = name;
        this.customer = customer;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public enum Status{
        NEW,
        PLA,
        INP,
        FIN;
    }

}
