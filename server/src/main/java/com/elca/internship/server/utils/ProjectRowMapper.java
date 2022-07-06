package com.elca.internship.server.utils;

import com.elca.internship.server.models.entity.Project;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectRowMapper implements RowMapper<Project> {
    @Override
    public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
        /*return new Project(
                rs.getLong(1),
                rs.getLong(2),
                rs.getInt(3),
                rs.getString(4),
                rs.getString(5),
                Project.Status.valueOf(rs.getString(6)),
                rs.getDate(7).toLocalDate(),
                rs.getDate(8).toLocalDate(),
                rs.getInt(9)
        );*/
        var project = new Project(
                rs.getLong(2),
                rs.getInt(3),
                rs.getString(4),
                rs.getString(5),
                Project.Status.valueOf(rs.getString(6)),
                rs.getDate(7).toLocalDate(),
                rs.getDate(8).toLocalDate()
        );

        project.setId(rs.getLong(1));
        project.setGroupId(rs.getInt(9));
        return project;
    }
}
