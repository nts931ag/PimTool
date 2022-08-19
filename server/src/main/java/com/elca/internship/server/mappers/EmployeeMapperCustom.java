package com.elca.internship.server.mappers;

import com.elca.internship.server.models.entity.Employee;
import com.elca.internship.server.models.record.EmployeeRecord;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class EmployeeMapperCustom {

    public EmployeeRecord entityToRecord(Employee entity){
        if(entity == null){
            return null;
        }
        return new EmployeeRecord(
                entity.getId(),
                entity.getVersion(),
                entity.getVisa(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getBirthDate()
        );
    }

    public List<EmployeeRecord> listEntityToListRecord(List<Employee> listEntity){
        if(listEntity == null){
            return Collections.emptyList();
        }

        var listRecord = new ArrayList<EmployeeRecord>();

        listEntity.forEach(employee -> listRecord.add(
                this.entityToRecord(employee)
        ));
        return listRecord;
    }
}
