package com.example.bb.redisdataaccessdemo.dao;

import com.example.bb.redisdataaccessdemo.domain.Student;

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

    Student selectOneByPrimaryKey(String id);
}
