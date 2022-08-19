package com.elca.internship.client.models;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import jiconfont.javafx.IconNode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;


@Data
@EqualsAndHashCode(of = {"lbProNumLink"})
public class ProjectTable extends Project{

    private CheckBox checkBox;
    private IconNode icDelete;
    private Label lbProNumLink;

    public ProjectTable(CheckBox checkBox, long id, long groupId, Integer projectNumber, String name, String customer, Status status, LocalDate startDate, LocalDate endDate, int version, IconNode icDelete) {
        super(id,groupId, projectNumber,name, customer,status,startDate,endDate,version);
        this.checkBox = checkBox;
        this.icDelete = icDelete;
        this.lbProNumLink = new Label(String.valueOf(projectNumber));
    }

}
