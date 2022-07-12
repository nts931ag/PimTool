package com.elca.internship.server.dao.impl;

import com.elca.internship.server.dao.ProjectDAO;
import com.elca.internship.server.models.entity.Project;
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
    public Project update(Long id, Project project) {
        var oldProject = findById(id);
        if(oldProject == null){
            return null;
        }

        final var sql = "UPDATE project " +
                "SET team_id=?, project_number=?, name=?, customer=?, status=?, start_date=?, end_date=?, version=? " +
                "WHERE id = ?";

        jdbcTemplate.update(sql, project.getGroupId(), project.getProjectNumber(), project.getName()
                , project.getCustomer(), project.getStatus(), Date.valueOf(project.getStartDate())
                , Date.valueOf(project.getEndDate()), project.getVersion(), project.getId());

        project.setId(oldProject.getId());
        return project;
    }

    @Override
    public Long insert(Project project) {
        simpleJdbcInsert.withTableName("project").usingGeneratedKeyColumns("id");

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("team_id", project.getGroupId())
                .addValue("projectNumber", project.getProjectNumber())
                .addValue("name", project.getName())
                .addValue("customer", project.getCustomer())
                .addValue("status", project.getStatus())
                .addValue("start_date", Date.valueOf(project.getStartDate()))
                .addValue("end_date", Date.valueOf(project.getEndDate()))
                .addValue("version", 1);

        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
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
}
