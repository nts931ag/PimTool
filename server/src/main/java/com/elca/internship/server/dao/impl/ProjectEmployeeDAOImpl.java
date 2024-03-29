package com.elca.internship.server.dao.impl;

import com.elca.internship.server.dao.ProjectEmployeeDAO;
import com.elca.internship.server.models.entity.ProjectEmployee;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectEmployeeDAOImpl implements ProjectEmployeeDAO {

    private final JdbcTemplate jdbcTemplate;
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
        try {
            simpleJdbcInsert.executeBatch(array);

        }catch (Exception e){
            e.printStackTrace();
        }
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

    @Override
    public void deleteEmployeesFromProjectEmployee(Long id, List<Long> values) {

        final var sql = "DELETE FROM project_employee where project_id = :id and employee_id NOT IN (:values)";
        var params = new MapSqlParameterSource().addValue("id", id);


        if(values.size() == 0){
            params.addValue("values", values.size());
        }else{
            params.addValue("values", values);
        }


        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void removeProjectEmployeeByProjectIds(List<Long> ids) {
        final var sql = "DELETE FROM project_employee where project_id in (:ids)";
        var params = new MapSqlParameterSource()
                .addValue("ids", ids);
        namedParameterJdbcTemplate.update(sql,params);
    }

    @Override
    public List<String> findVisaAndNameByProjectId(Long projectId) {
        final var sql = "SELECT visa, first_name, last_name " +
                "FROM employee e join project_employee pe on (e.id = pe.employee_id and pe.project_id = :projectId)";

        var params = new MapSqlParameterSource()
                .addValue("projectId", projectId);

        return namedParameterJdbcTemplate.query(sql, params, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString(1) + ": " + rs.getString(2) + " " + rs.getString(3);
            }
        });
    }

    @Override
    public void saveNewEmployeesToProjectEmployee(Long id, ArrayList<Long> listId) {
        final var sql = "INSERT INTO project_employee (project_id, employee_id)\n" +
                "SELECT * FROM (SELECT :projectId AS project_id, :employeeId as employee_id) AS tmp\n" +
                "WHERE NOT EXISTS (\n" +
                "    SELECT * FROM project_employee WHERE project_id = :projectId and employee_id = :employeeId\n" +
                ") LIMIT 1;";

        List<MapSqlParameterSource> entries = new ArrayList<>();
        for(var i = 0; i < listId.size(); ++i){
            entries.add(new MapSqlParameterSource()
                    .addValue("projectId", id)
                    .addValue("employeeId", listId.get(i)
                    ));
        }
        var array = entries.toArray(new MapSqlParameterSource[entries.size()]);
        namedParameterJdbcTemplate.batchUpdate(sql, array);
    }


}
