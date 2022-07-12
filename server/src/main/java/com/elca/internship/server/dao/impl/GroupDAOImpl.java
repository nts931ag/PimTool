package com.elca.internship.server.dao.impl;

import com.elca.internship.server.dao.GroupDAO;
import com.elca.internship.server.models.entity.Group;
import com.elca.internship.server.utils.GroupRowMapper;
import com.elca.internship.server.utils.ProjectRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GroupDAOImpl implements GroupDAO {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Group> findAll() {
        final var sql = "SELECT * FROM team";



        return jdbcTemplate.query(sql, new GroupRowMapper());
    }
}
