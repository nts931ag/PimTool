package com.elca.internship.server.dao.impl;

import com.elca.internship.server.dao.EmployeeDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmployeeDAOImpl implements EmployeeDAO {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Long> findIdBylistVisa(List<String> listVisa) {
        var parameters = new MapSqlParameterSource("listVisa", listVisa);
        return namedParameterJdbcTemplate.query(
                "SELECT id FROM EMPLOYEE WHERE visa IN (:listVisa)",
                parameters,
                (rs, rowNum) -> rs.getLong("id")
        );
    }
}
