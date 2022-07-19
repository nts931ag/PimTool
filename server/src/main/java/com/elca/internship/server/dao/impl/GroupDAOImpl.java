package com.elca.internship.server.dao.impl;

import com.elca.internship.server.dao.GroupDAO;
import com.elca.internship.server.models.entity.Group;
import com.elca.internship.server.utils.GroupRowMapper;
import com.elca.internship.server.utils.ProjectRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GroupDAOImpl implements GroupDAO {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Group> findAll() {
        final var sql = "SELECT * FROM team";



        return jdbcTemplate.query(sql, new GroupRowMapper());
    }

    @Override
    public Long insert(Group group) {
        if(simpleJdbcInsert.isCompiled() == false){
            simpleJdbcInsert.withTableName("team").usingGeneratedKeyColumns("id");
        }

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("team_leader_id", group.getGroupLeaderId())
                .addValue("version", 1);

        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }

    @Override
    public Group findById(long groupId) {
        var sql = "SELECT * FROM team WHERE id = :groupId";
        var params = new MapSqlParameterSource()
                .addValue("groupId", groupId);
        return namedParameterJdbcTemplate.queryForObject(sql, params, new GroupRowMapper());
    }
}
