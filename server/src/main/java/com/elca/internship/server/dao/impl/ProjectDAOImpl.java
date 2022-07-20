package com.elca.internship.server.dao.impl;

import com.elca.internship.server.dao.ProjectDAO;
import com.elca.internship.server.models.entity.Project;
import com.elca.internship.server.models.exceptions.ProjectNumberAlreadyExistedException;
import com.elca.internship.server.utils.ProjectRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectDAOImpl implements ProjectDAO {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public Project save(Project project) {
        return null;
    }

    @Override
    public int update(Long id, Project project) {
        var oldProject = findById(id);
        if(oldProject == null){
            return -1;
        }

        final var sql = "UPDATE project" +
                " SET team_id=:groupId" +
                ", project_number=:proNum" +
                ", name=:proName" +
                ", customer=:proCus" +
                ", status=:proSta" +
                ", start_date=:proStart" +
                ", end_date=:proEnd" +
                ", version=:proVersion" +
                " WHERE id =:proId;";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("proId", project.getId())
                .addValue("groupId", project.getGroupId())
                .addValue("proNum", project.getProjectNumber())
                .addValue("proName", project.getName())
                .addValue("proCus", project.getCustomer())
                .addValue("proSta", project.getStatus().toString())
                .addValue("proStart", Date.valueOf(project.getStartDate()))
                .addValue("proEnd", Date.valueOf(project.getEndDate()))
                .addValue("proVersion", oldProject.getVersion() + 1)
                ;


        return namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public Long insert(Project project) throws ProjectNumberAlreadyExistedException {
        if(simpleJdbcInsert.isCompiled() == false){
            simpleJdbcInsert.withTableName("project").usingGeneratedKeyColumns("id");
        }

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("team_id", project.getGroupId())
                .addValue("projectNumber", project.getProjectNumber())
                .addValue("name", project.getName())
                .addValue("customer", project.getCustomer())
                .addValue("status", project.getStatus().toString())
                .addValue("start_date", Date.valueOf(project.getStartDate()))
                .addValue("end_date", Date.valueOf(project.getEndDate()))
                .addValue("version", 1);
        try{
            return simpleJdbcInsert.executeAndReturnKey(params).longValue();
        }catch (Exception e){
            throw  new ProjectNumberAlreadyExistedException(project.getProjectNumber());
        }
    }

    @Override
    public Project findById(Long id) {
        final var sql = "SELECT * FROM project WHERE id = :id";
        var namedParameters = new MapSqlParameterSource().addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new ProjectRowMapper());
    }

    @Override
    public List<Project> findAll() {
        final var sql = "SELECT * FROM project";
        return jdbcTemplate.query(sql, new ProjectRowMapper());
    }

    @Override
    public void deleteById(long id) {
        final var sql = "DELETE FROM project WHERE id = :id";
        var namedParameters = new MapSqlParameterSource().addValue("id", id);
        namedParameterJdbcTemplate.update(sql, namedParameters);
    }

    @Override
    public List<Project> findByProNameAndProStatus(String proName, String proStatus) {
        final var sql = "SELECT DISTINCT * FROM project where name like :proName and status like :proStatus";
        var namedParameters = new MapSqlParameterSource()
                .addValue("proName", proName)
                .addValue("proStatus", proStatus);
        return namedParameterJdbcTemplate.query(sql, namedParameters, new ProjectRowMapper());
    }

    @Override
    public List<Project> findByStatus(String proStatus) {
        final var sql = "SELECT DISTINCT * FROM project where status like :proStatus";
        var namedParameters = new MapSqlParameterSource()
                .addValue("proStatus", proStatus);
        return namedParameterJdbcTemplate.query(sql, namedParameters, new ProjectRowMapper());
    }

    @Override
    public List<Project> findByProName(String proName) {
        final var sql = "SELECT DISTINCT * FROM project where name like :proName";
        var namedParameters = new MapSqlParameterSource()
                .addValue("proName", proName);
        return namedParameterJdbcTemplate.query(sql, namedParameters, new ProjectRowMapper());
    }

    @Override
    public List<Project> findByProNum(String proCriteria) {
        final var sql = "SELECT DISTINCT * FROM project where project_number = :proCriteria";
        var namedParameters = new MapSqlParameterSource()
                .addValue("proCriteria", proCriteria);
        return namedParameterJdbcTemplate.query(sql, namedParameters, new ProjectRowMapper());
    }

    @Override
    public List<Project> findByProNumAndProStatus(String proCriteria, String proStatus) {
        final var sql = "SELECT DISTINCT * FROM project where project_number = :proCriteria and status like :proStatus";
        var namedParameters = new MapSqlParameterSource()
                .addValue("proCriteria", proCriteria)
                .addValue("proStatus", proStatus);
        return namedParameterJdbcTemplate.query(sql, namedParameters, new ProjectRowMapper());
    }

    //:proCriteria

    @Override
    public List<Project> findByProCriteria(String proCriteria) {
        final var sql = "SELECT DISTINCT * FROM project where lower(name) like :proCriteria or lower(customer) like :proCriteria";
        var param = "%" + proCriteria.toLowerCase().trim() + "%";
        var namedParameters = new MapSqlParameterSource()
                .addValue("proCriteria", param);
        return namedParameterJdbcTemplate.query(sql, namedParameters, new ProjectRowMapper());
    }


    @Override
    public List<Project> findByProCriteriaAndProStatus(String proCriteria, String proStatus) {
        final var sql = "SELECT DISTINCT * FROM project where (lower(name) like :proCriteria or lower(customer) like :proCriteria) and status like :proStatus";
        var param = "%" + proCriteria.toLowerCase().trim() + "%";
        var namedParameters = new MapSqlParameterSource()
                .addValue("proCriteria", param)
                .addValue("proStatus", proStatus);
        return namedParameterJdbcTemplate.query(sql, namedParameters, new ProjectRowMapper());
    }
}
