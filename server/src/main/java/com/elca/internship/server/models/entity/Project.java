package com.elca.internship.server.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project extends BaseEntity{
    private long groupId;
    private Integer projectNumber;
    private String name;
    private String customer;
    private Status status;
    private LocalDate startDate;
    private LocalDate endDate;

    public Project(long id, long groupId, Integer projectNumber, String name, String customer, Status status, LocalDate startDate, LocalDate endDate, int version) {
        super(id, version);
        this.groupId = groupId;
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
