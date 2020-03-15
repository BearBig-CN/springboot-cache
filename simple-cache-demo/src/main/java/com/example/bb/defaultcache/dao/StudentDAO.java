package com.example.bb.defaultcache.dao;

import com.example.bb.defaultcache.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
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
    @CacheEvict(cacheNames = "students", key = "T(org.springframework.cache.interceptor.SimpleKey).EMPTY")
    public Optional<Student> insert(Student stu) {
        int affectedRows = jdbcTemplate.update("insert into student(stu_name, stu_age, stu_birthday) values(?,?,?)", stu.getName(), stu.getAge(), stu.getBirthday());
        if (affectedRows == 1) {
            List<Student> students = query("select * from student where stu_name=?", stu.getName());
            return Optional.ofNullable(students.get(0));
        }
        return Optional.empty();
    }

    @Override
    @Cacheable(cacheNames = "students")
    public List<Student> selectAll() {
        return query("select * from student");
    }

    @Override
    @Cacheable(cacheNames = "students", key = "#id")
    public Student selectOneByPrimaryKey(Integer id) {
        List<Student> students = query("select * from student where stu_id=?", id);
        return CollectionUtils.isEmpty(students) ? null : students.get(0);
    }

    /**
     * 内部调用，不会缓存
     *
     * @return 返回所有结果集
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
    @CacheEvict(cacheNames = "students", key = "#id")
    public boolean deleteByPrimaryKey(Integer id) {
        int successCount = jdbcTemplate.update("delete from student where stu_id=?", id);
        return successCount == 1;
    }

    @Override
    @CachePut(cacheNames = "students", key = "#id")
    public Student updateByPrimaryKey(Integer id, String name) {
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
                .stuId(resultSet.getInt("stu_id"))
                .name(resultSet.getString("stu_name"))
                .age(resultSet.getInt("stu_age"))
                .birthday(resultSet.getDate("stu_birthday"))
                .build();
    }
}
