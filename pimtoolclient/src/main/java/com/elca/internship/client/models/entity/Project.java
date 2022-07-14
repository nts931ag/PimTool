package com.elca.internship.client.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Project extends BaseEntity{
    private long groupId;
    private int projectNumber;
    private String name;
    private String customer;
    private Status status;
    private LocalDate startDate;
    private LocalDate endDate;

    public Project(long groupId, int projectNumber, String name, String customer, Status status, LocalDate startDate, LocalDate endDate, int version) {
        super(0L,version);
        this.groupId = groupId;
        this.projectNumber = projectNumber;
        this.name = name;
        this.customer = customer;
        this.status = status;
        this.startDate =startDate;
        this.endDate = endDate;
    }

    public Project(long id, long groupId, int projectNumber, String name, String customer, Status status, LocalDate startDate, LocalDate endDate, int version) {
        super(id,version);
        this.groupId = groupId;
        this.projectNumber = projectNumber;
        this.name = name;
        this.customer = customer;
        this.status = status;
        this.startDate =startDate;
        this.endDate = endDate;
    }


    public enum Status{
        NEW,
        PLA,
        INP,
        FIN;

        public static Status getStatus(String status){
            return switch (status){
                case "New" ->
                        Status.NEW;
                case "Planned" ->
                        Status.PLA;
                case "In progress" ->
                        Status.INP;
                case "Finished" ->
                        Status.FIN;
                default -> null;
            };
        }

    }

}
