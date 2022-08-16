package com.elca.internship.server.mappers;

import com.elca.internship.server.models.dto.EmployeeDto;
import com.elca.internship.server.models.entity.Employee;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeMapperCustom {

    public EmployeeDto entityToDto(Employee entity){
        if(entity == null){
            return null;
        }
        return new EmployeeDto(
                entity.getId(),
                entity.getVersion(),
                entity.getVisa(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getBirthDate()
        );
    }

    public List<EmployeeDto> listEntityToListDto(List<Employee> listEntity){
        if(listEntity == null){
            return null;
        }
        var listDto = new ArrayList<EmployeeDto>();
        listEntity.stream().forEach(Employee -> {
            listDto.add(
                    this.entityToDto(Employee)
            );
        });
        return listDto;
    }
}
