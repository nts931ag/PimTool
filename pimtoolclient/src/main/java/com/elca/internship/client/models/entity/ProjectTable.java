package com.elca.internship.client.models.entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;
import jiconfont.javafx.IconFontFX;
import jiconfont.javafx.IconNode;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;


@Data
@AllArgsConstructor
public class ProjectTable{

    private CheckBox checkBox;
    private long groupId;
    private Integer projectNumber;
    private String name;
    private String customer;
    private Project.Status status;
    private LocalDate startDate;
    private LocalDate endDate;
    private int version;
    private IconNode icDelete;





}
