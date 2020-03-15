package com.example.bb.defaultcache.dao;

import com.example.bb.defaultcache.domain.Student;

import java.util.List;
import java.util.Optional;

/**
 * @author BB
 * Create: 2020/3/14 0014 17:29
 */
public interface IStudentDAO {

    Optional<Student> insert(Student stu);

    List<Student> selectAll();

    List<Student> selectAll2();

    List<Student> selectAll3();

    Student selectOneByPrimaryKey(Integer id);

    boolean deleteByPrimaryKey(Integer id);

    Student updateByPrimaryKey(Integer id, String name);
}
