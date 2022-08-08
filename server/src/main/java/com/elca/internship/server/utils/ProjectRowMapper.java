package com.elca.internship.server.utils;

import com.elca.internship.server.models.Status;
import com.elca.internship.server.models.entity.Project;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ProjectRowMapper implements RowMapper<Project> {
    @Override
    public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
        Date date = rs.getDate(8);
        LocalDate localDate = null;
        if(date != null){
            localDate = date.toLocalDate();
        }
        return new Project(
                rs.getLong(1),
                rs.getLong(2),
                rs.getInt(3),
                rs.getString(4),
                rs.getString(5),
                Status.valueOf(rs.getString(6)),
                rs.getDate(7).toLocalDate(),
                localDate,
                rs.getInt(9)
        );


    }
}
