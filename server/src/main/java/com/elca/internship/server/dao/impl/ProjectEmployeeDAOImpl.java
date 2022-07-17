package com.elca.internship.server.dao.impl;

import com.elca.internship.server.dao.ProjectEmployeeDAO;
import com.elca.internship.server.models.entity.ProjectEmployee;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectEmployeeDAOImpl implements ProjectEmployeeDAO {


    private final SimpleJdbcInsert simpleJdbcInsert;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void saveProjectEmployee(Long idProject, List<Long> listMember) {
        if(simpleJdbcInsert.isCompiled() == false){
            simpleJdbcInsert.withTableName("PROJECT_EMPLOYEE");
        }
        List<MapSqlParameterSource> entries = new ArrayList<>();
        for(var i = 0; i < listMember.size(); ++i){
            entries.add(new MapSqlParameterSource()
                    .addValue("projectId", idProject)
                    .addValue("employeeId", listMember.get(i)
                    ));
        }
        var array = entries.toArray(new MapSqlParameterSource[entries.size()]);
        simpleJdbcInsert.executeBatch(array);
    }

    @Override
    public void deleteProjectEmployeeByProjectId(Long id) {
        final var sql = "DELETE FROM project_employee WHERE project_id IN (:id)";
        var namedParameters = new MapSqlParameterSource().addValue("id", id);
        namedParameterJdbcTemplate.update(sql, namedParameters);
    }

    @Override
    public List<String> findAllEmployeeVisaByProjectId(Long projectId) {
        final var sql = "SELECT visa " +
                "FROM employee e join project_employee pe on e.id = pe.employee_id " +
                "where pe.project_id = :projectId;";
        var nameParameters = new MapSqlParameterSource().addValue("projectId", projectId);

        return namedParameterJdbcTemplate.queryForList(sql, nameParameters, String.class);
    }
}
