package com.example.bb.common.controller;

import com.example.bb.common.domain.Student;
import com.example.bb.common.service.IStudentService;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private IStudentService iStudentService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createCourse(Student stu) {
        log.info("调用了createCourse");
        try {
            iStudentService.save(stu);
        } catch (Exception ex) {
            log.error("save student object to database is fail!", ex);
            return "save student to database is fail!";
        }
        return "save student to database is success!";
    }

    @JsonAnyGetter
    @GetMapping(value = "/obtain", params = "type=cache")
    public List<Student> obtainAllByCache(String type) {
        log.info("调用了obtainAllByCache");
        return iStudentService.queryAllByCache();
    }

    @JsonAnyGetter
    @GetMapping(value = "/obtain", params = "type=unCache")
    public List<Student> obtainAllNotByCache(String type) {
        log.info("调用了obtainAllNotByCache");
        return iStudentService.queryAllNotByCache();
    }

    @JsonAnyGetter
    @GetMapping("/obtainOne/{id}")
    public Student obtainOne(@PathVariable String id) {
        log.info("调用了obtainOne");
        return iStudentService.queryOneByPrimaryKey(id);
    }

    @JsonAnyGetter
    @DeleteMapping("/{id}")
    public boolean removeById(@PathVariable String id) {
        log.info("调用了removeById");
        return iStudentService.removeByPrimaryKey(id);
    }

    @JsonAnyGetter
    @PutMapping("/{id}/{name}")
    public Student updateNameById(@PathVariable String id, @PathVariable String name) {
        log.info("调用了updateNameById");
        return iStudentService.modifyByPrimaryKey(id, name);
    }
}
