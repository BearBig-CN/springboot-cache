package com.example.bb.redisdataaccessdemo.dao;

import com.example.bb.redisdataaccessdemo.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * @author BB
 * Create: 2020/3/14 9:17
 */
@Repository
public class StudentDAO extends AbstractBasicDAO<Student> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<Student> insert(Student stu) {
        int affectedRows = jdbcTemplate.update("insert into student(stu_name, stu_age, stu_birthday) values(?,?,?)", stu.getName(), stu.getAge(), stu.getBirthday());
        if (affectedRows == 1) {
            List<Student> students = query("select * from student where stu_name=?", stu.getName());
            return Optional.ofNullable(students.get(0));
        }
        return Optional.empty();
    }

    public Optional<List<Student>> selectAll() {
        List<Student> students = query("select * from student");
        return Optional.ofNullable(students);
    }

    public Optional<Student> selectOneByPrimaryKey(String id) {
        List<Student> eList = query("select * from student where stu_id=?", Integer.valueOf(id));
        return CollectionUtils.isEmpty(eList) ? Optional.empty() : Optional.of(eList.get(0));
    }

    @Override
    protected JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    @Override
    protected Student generatorE(ResultSet resultSet, int i) throws SQLException {
        return Student.builder()
                .stuId(resultSet.getInt("stu_id"))
                .name(resultSet.getString("stu_name"))
                .age(resultSet.getInt("stu_age"))
                .birthday(resultSet.getDate("stu_birthday"))
                .build();
    }
}
