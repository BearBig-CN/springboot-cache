package com.example.bb.redis.dao;

import com.example.bb.redis.domain.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author BB
 * Create: 2020/3/13 20:30
 */
@Repository
public class CourseDAO extends AbstractBasicDAO<Course> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Course insert(String courseName) {
        int affectedRows = jdbcTemplate.update("insert into course(name) values(?)", courseName);
        if (affectedRows == 1) {
            List<Course> courses = query("select * from course where name=?", courseName);
            if (!CollectionUtils.isEmpty(courses)) {
                return courses.get(0);
            }
        }
        return null;
    }

    public List<Course> selectAll() {
        return query("select * from course");
    }

    public Course selectOneByPrimaryKey(String id) {
        List<Course> courses = query("select * from course where id=?", Integer.valueOf(id));
        if (!CollectionUtils.isEmpty(courses)) {
            return courses.get(0);
        }
        return null;
    }

    @Override
    protected JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    @Override
    protected Course generatorE(ResultSet resultSet, int i) throws SQLException {
        return Course.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .build();
    }
}
