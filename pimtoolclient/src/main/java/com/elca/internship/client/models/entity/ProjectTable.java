package com.elca.internship.client.models.entity;

import javafx.scene.control.CheckBox;
import jiconfont.javafx.IconNode;
import lombok.Data;

import java.time.LocalDate;


@Data

public class ProjectTable extends Project{

    private CheckBox checkBox;
    private IconNode icDelete;

    public ProjectTable( CheckBox checkBox, long groupId, Integer projectNumber, String name, String customer, Status status, LocalDate startDate, LocalDate endDate, int version, IconNode icDelete) {
        super(groupId, projectNumber,name, customer,status,startDate,endDate,version);
        this.checkBox = checkBox;
        this.icDelete = icDelete;
    }

}
