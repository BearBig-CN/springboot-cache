package com.example.bb.redis.controller;

import com.example.bb.redis.dao.StudentDAO;
import com.example.bb.redis.domain.Student;
import com.example.bb.redis.util.DataCacheUtil;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
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

    private final static String ALL_KEY = "stu:all";
    private final static String ID_KEY = "stu:id:";

    private static final Object PRESENT = "not found!";

    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    private ValueOperations<String, Object> studentOps;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createCourse(Student stu) {
        try {
            Optional<Student> stuOpt = studentDAO.insert(stu);
            stuOpt.ifPresent(student -> DataCacheUtil.cachedData(studentOps, ALL_KEY, student, 3600));
        } catch (Exception ex) {
            log.error("创建课程异常,{}", ex);
            return "create student fail";
        }
        return "create student success";
    }

    @JsonAnyGetter
    @GetMapping("/obtain")
    public Object obtainAll() {
        Object result = studentOps.get(ALL_KEY);
        if (ObjectUtils.isEmpty(result)) {
            Optional<List<Student>> stuListOpt = studentDAO.selectAll();
            if (stuListOpt.isPresent() && !CollectionUtils.isEmpty(stuListOpt.get())) {
                DataCacheUtil.cachedData(studentOps, ALL_KEY, stuListOpt.get(), 3600);
            }
            result = stuListOpt.orElse(null);
        }
        return result;
    }

    @JsonAnyGetter
    @GetMapping("/obtainOne/{id}")
    public Object obtainOne(@PathVariable String id) {
        // 去缓存中获取
        Object cacheData = studentOps.get(ID_KEY + id);
        // 缓存中不存在时，去数据库中获取
        if (ObjectUtils.isEmpty(cacheData)) {
            Optional<Student> stuOpt = studentDAO.selectOneByPrimaryKey(id);
            // 添加到缓存
            if (stuOpt.isPresent()) {
                cacheData = stuOpt.get();
            } else {
                // 防止恶意缓存穿透，可以设置一个固定的值
                cacheData = PRESENT;
            }
            DataCacheUtil.cachedData(studentOps, ID_KEY + id, cacheData, 30);
        }
        return cacheData;
    }
}
