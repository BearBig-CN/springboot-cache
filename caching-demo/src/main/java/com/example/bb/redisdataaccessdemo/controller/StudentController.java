package com.example.bb.redisdataaccessdemo.controller;

import com.example.bb.redisdataaccessdemo.dao.StudentDAO;
import com.example.bb.redisdataaccessdemo.domain.Student;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * 对外接口
 *
 * @author BB
 * Create: 2020/3/13 20:50
 */
@Slf4j
@RestController
@RequestMapping("/stu")
public class StudentController {

    @Autowired
    private StudentDAO studentDAO;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createCourse(Student stu) {
        try {
            Optional<Student> stuOpt = studentDAO.insert(stu);
        } catch (Exception ex) {
            log.error("创建课程异常,{}", ex);
            return "create student fail";
        }
        return "create student success";
    }

    @JsonAnyGetter
    @GetMapping(value = "/obtain", params = "type=1" )
    public List<Student> obtainAll(int type) {
        log.info("调用了obtain1");
        return studentDAO.selectAll();
    }

    @JsonAnyGetter
    @GetMapping(value = "/obtain", params = "type=2")
    public List<Student> obtainAll2() {
        log.info("调用了obtain2");
        return studentDAO.selectAll2();
    }

    @JsonAnyGetter
    @GetMapping(value = "/obtain", params = "type=3")
    public List<Student> obtainAll3() {
        log.info("调用了obtain3");
        return studentDAO.selectAll3();
    }

    @JsonAnyGetter
    @GetMapping("/obtainOne/{id}")
    public Student obtainOne(@PathVariable String id) {
        return studentDAO.selectOneByPrimaryKey(id);
    }
}
