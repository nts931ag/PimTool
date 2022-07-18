package com.elca.internship.client.models.entity;

import com.elca.internship.client.i18n.I18nKey;
import com.elca.internship.client.i18n.I18nManager;
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
        NEW("project.status.new"),
        PLA("project.status.pla"),
        INP("project.status.inp"),
        FIN("project.status.fin");


        public static Status convertStringStatusToStatus(String value, I18nManager i18nManager){
            if(value.equalsIgnoreCase(i18nManager.text(I18nKey.COMBOBOX_NEW_PROJECT_STATUS))){
                return Status.NEW;
            }else if(value.equalsIgnoreCase(i18nManager.text(I18nKey.COMBOBOX_IN_PROGRESS_PROJECT_STATUS))){
                return Status.INP;
            }else if(value.equalsIgnoreCase(i18nManager.text(I18nKey.COMBOBOX_PLANNED_PROJECT_STATUS))){
                return Status.PLA;
            }else if(value.equalsIgnoreCase(i18nManager.text(I18nKey.COMBOBOX_FINISHED_PROJECT_STATUS))){
                return Status.FIN;
            }else{
                return null;
            }
        }

        public static I18nKey convertStatusToI18nKey(Status status){
            switch (status){
                case NEW -> {
                    return I18nKey.COMBOBOX_NEW_PROJECT_STATUS;
                }

                case PLA -> {
                    return I18nKey.COMBOBOX_PLANNED_PROJECT_STATUS;
                }

                case INP -> {
                    return I18nKey.COMBOBOX_IN_PROGRESS_PROJECT_STATUS;
                }

                case FIN -> {
                    return I18nKey.COMBOBOX_FINISHED_PROJECT_STATUS;
                }

                default -> {
                    return null;
                }

            }
        }


        private final String key;

        Status(final String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }

    }

}
