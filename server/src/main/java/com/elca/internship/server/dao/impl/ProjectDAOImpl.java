package com.elca.internship.server.dao.impl;

import com.elca.internship.server.dao.ProjectDAO;
import com.elca.internship.server.models.entity.Project;
import com.elca.internship.server.exceptions.ProjectNumberAlreadyExistedException;
import com.elca.internship.server.utils.ProjectRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
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

        var localDate = project.getEndDate();
        Date date = null;
        if(localDate != null){
            date = Date.valueOf(localDate);
        }

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("proId", project.getId())
                .addValue("groupId", project.getGroupId())
                .addValue("proNum", project.getProjectNumber())
                .addValue("proName", project.getName())
                .addValue("proCus", project.getCustomer())
                .addValue("proSta", project.getStatus().toString())
                .addValue("proStart", Date.valueOf(project.getStartDate()))
                .addValue("proEnd", date)
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

                .addValue("version", 1);

        if(project.getEndDate() != null){
            params.addValue("end_date", Date.valueOf(project.getEndDate()));
        }
        try{
            return simpleJdbcInsert.executeAndReturnKey(params).longValue();
        }catch (Exception e){
            throw new ProjectNumberAlreadyExistedException(project.getProjectNumber());
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
        final var sql = """
                SELECT DISTINCT * FROM project 
                where project_number = :proCriteria 
                        and status like :proStatus
                """;
        var proStringCriteria = "%" + proCriteria + "%";
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
    public void deleteByIds(List<Long> ids) {
        final var sql = "DELETE FROM project where id IN (:ids)";
        var params = new MapSqlParameterSource().addValue("ids", ids);
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public Integer findProjectNumber(Integer proNum) {
        final var sql = "SELECT project_number FROM project where project_number = :proNum";
        var namedParameters = new MapSqlParameterSource()
                .addValue("proNum", proNum);
        return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM project", Integer.class);
    }

    @Override
    public Page<Project> findAllProjectWithPagination(Pageable pageable) {
        int count = this.count();
        Order order = !pageable.getSort().isEmpty() ? pageable.getSort().toList().get(0) : Order.by("project_number");
        List<Project> projects = jdbcTemplate.query("SELECT * FROM PROJECT ORDER BY " + order.getProperty() + " "
        + order.getDirection().name() + " LIMIT " + pageable.getPageSize() + " OFFSET " + pageable.getOffset(),
                new ProjectRowMapper());
        return new PageImpl<>(projects, pageable, count);
    }

    @Override
    public Page<Project> findAllProjectSpecifiedWithPagination(String proCriteria, String proStatus, Pageable pageable) {
        int count = this.count();
        if(proCriteria.isBlank() && proStatus.isBlank()){
            proCriteria = "%";
            proStatus = "%";
        }else {
            if(proCriteria.isBlank()){
                proCriteria = "%";
                proStatus = "%"+proStatus+"%";
            }
            if(proStatus.isBlank()){
                proCriteria = "%"+proCriteria+"%";
                proStatus = "%";
            }
        }


        var sql = "SELECT * FROM PROJECT " +
                "WHERE (CAST(project_number as CHAR(50)) like :proCriteria or lower(name) like :proCriteria or lower(customer) like :proCriteria) " +
                "and status like :proStatus " +
                "ORDER BY project_number ASC LIMIT :pageSize OFFSET :pageOffSet;";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("pageSize", pageable.getPageSize())
                .addValue("pageOffSet", pageable.getOffset())
                .addValue("proCriteria", proCriteria)
                .addValue("proStatus", proStatus);

        List<Project> projects = namedParameterJdbcTemplate.query(sql, parameterSource, new ProjectRowMapper());
        return new PageImpl<Project>(projects, pageable, count);
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
