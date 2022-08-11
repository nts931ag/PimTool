package com.elca.internship.server.utils;

import com.elca.internship.server.models.entity.Group;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupRowMapper implements RowMapper<Group> {
    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
//        return new Group(rs.getLong(1), rs.getLong(2), rs.getInt(3));
        return null;
    }
}
