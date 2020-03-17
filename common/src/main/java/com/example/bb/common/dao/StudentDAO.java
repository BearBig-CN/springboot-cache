package com.example.bb.common.dao;

import com.example.bb.common.domain.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Dao的具体实现
 *
 * @author BB
 * Create: 2020/3/14 9:17
 */
@Slf4j
@Component
public class StudentDAO extends AbstractBasicDAO<Student> implements IStudentDAO {

    private JdbcTemplate jdbcTemplate;
    @Resource
    private IStudentDAO iStudentDAO;

    public StudentDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Student insert(Student stu) {
        int affectedRows = jdbcTemplate.update("insert into student(stu_id, stu_name, stu_age, stu_birthday) values(?,?,?,?)", stu.getStuId(), stu.getName(), stu.getAge(), stu.getBirthday());
        if (affectedRows == 1) {
            Student newStu = queryOne("select * from student where stu_id=?", stu.getStuId());
            log.info("new student info is {}", newStu);
            return newStu;
        }
        return null;
    }


    @Override
    public Student selectOneByPrimaryKey(String id) {
        List<Student> students = query("select * from student where stu_id=?", id);
        return CollectionUtils.isEmpty(students) ? null : students.get(0);
    }

    @Override
    public List<Student> selectAll() {
        return query("select * from student");
    }
    
    @Override
    public boolean deleteByPrimaryKey(String id) {
        int successCount = jdbcTemplate.update("delete from student where stu_id=?", id);
        return successCount == 1;
    }

    @Override
    public Student updateByPrimaryKey(String id, String name) {
        Student stu = null;
        int successCount = jdbcTemplate.update("update student set stu_name=? where stu_id=?", name, id);
        if (successCount == 1) {
            List<Student> students = query("select * from student where stu_id=?", id);
            stu = students.isEmpty() ? null : students.get(0);
        }
        return stu;
    }

    @Override
    protected JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    @Override
    protected Student generatorE(ResultSet resultSet, int i) throws SQLException {
        return Student.builder()
                .stuId(resultSet.getString("stu_id"))
                .name(resultSet.getString("stu_name"))
                .age(resultSet.getInt("stu_age"))
                .birthday(resultSet.getDate("stu_birthday"))
                .build();
    }
}
