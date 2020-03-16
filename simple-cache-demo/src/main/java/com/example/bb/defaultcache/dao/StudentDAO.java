package com.example.bb.defaultcache.dao;

import com.example.bb.common.dao.AbstractBasicDAO;
import com.example.bb.common.dao.IStudentDAO;
import com.example.bb.common.domain.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
    @Caching(
            cacheable = {@Cacheable(cacheNames = "students", key = "#stu.stuId")},
            evict = {@CacheEvict(cacheNames = "students", key = "T(org.springframework.cache.interceptor.SimpleKey).EMPTY", beforeInvocation = true)}
    )
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
    @Cacheable(cacheNames = "students")
    public List<Student> selectAllByCache() {
        return query("select * from student");
    }

    @Override
    @Cacheable(cacheNames = "students", key = "#id")
    public Student selectOneByPrimaryKey(String id) {
        List<Student> students = query("select * from student where stu_id=?", id);
        return CollectionUtils.isEmpty(students) ? null : students.get(0);
    }

    /**
     * 内部调用，不会缓存
     *
     * @return 返回所有结果集
     */
    @Override
    public List<Student> selectAllNotByCache() {
        // 内部调用此方法，不会缓存
        return selectAllByCache();
    }

    @Override
    public List<Student> selectAllByCache2() {
        // 调用此返回，会缓存，因为是spring会通过代理调用
        return iStudentDAO.selectAllByCache();
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "students", key = "#id"),
            @CacheEvict(cacheNames = "students", key = "T(org.springframework.cache.interceptor.SimpleKey).EMPTY")
    })
    public boolean deleteByPrimaryKey(String id) {
        int successCount = jdbcTemplate.update("delete from student where stu_id=?", id);
        return successCount == 1;
    }

    @Override
    @Caching(
            put = {@CachePut(cacheNames = "students", key = "#id")},
            evict = {@CacheEvict(cacheNames = "students", key = "T(org.springframework.cache.interceptor.SimpleKey).EMPTY")}
    )
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
