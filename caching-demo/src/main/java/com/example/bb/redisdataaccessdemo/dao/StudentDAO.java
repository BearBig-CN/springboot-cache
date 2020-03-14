package com.example.bb.redisdataaccessdemo.dao;

import com.example.bb.redisdataaccessdemo.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * @author BB
 * Create: 2020/3/14 9:17
 */
@Component
public class StudentDAO extends AbstractBasicDAO<Student> implements IStudentDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IStudentDAO studentDAO;

    @Override
    public Optional<Student> insert(Student stu) {
        int affectedRows = jdbcTemplate.update("insert into student(stu_name, stu_age, stu_birthday) values(?,?,?)", stu.getName(), stu.getAge(), stu.getBirthday());
        if (affectedRows == 1) {
            List<Student> students = query("select * from student where stu_name=?", stu.getName());
            return Optional.ofNullable(students.get(0));
        }
        return Optional.empty();
    }

    @Override
    @Cacheable("students")
    public List<Student> selectAll() {
        return query("select * from student");
    }

    @Override
    @Cacheable(value = "students", key = "#id")
    public Student selectOneByPrimaryKey(String id) {
        List<Student> students = query("select * from student where stu_id=?", Integer.valueOf(id));
        return CollectionUtils.isEmpty(students) ? null : students.get(0);
    }

    /**
     * 内部调用，不会缓存
     *
     * @return
     */
    @Override
    public List<Student> selectAll2() {
        // 内部调用此方法，不会缓存
        return selectAll();
    }

    @Override
    public List<Student> selectAll3() {
        // 调用此返回，会缓存，因为是spring会通过代理调用
        return studentDAO.selectAll();
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
