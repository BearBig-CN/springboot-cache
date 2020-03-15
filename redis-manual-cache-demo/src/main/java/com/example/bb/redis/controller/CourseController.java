package com.example.bb.redis.controller;

import com.example.bb.redis.dao.CourseDAO;
import com.example.bb.redis.domain.Course;
import com.example.bb.redis.util.DataCacheUtil;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 对外接口
 *
 * @author BB
 * Create: 2020/3/13 20:50
 */
@Slf4j
@RestController
@RequestMapping("/course")
public class CourseController {

    private final static String COURSE_ALL_KEY = "course:all";
    private final static String COURSE_ID_KEY = "course:id:";

    @Autowired
    private CourseDAO courseDAO;

    @Autowired
    private ValueOperations<String, Object> courseOps;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createCourse(String courseName) {
        try {
            Course course = courseDAO.insert(courseName);
            DataCacheUtil.cachedData(courseOps, COURSE_ALL_KEY, course, 0);
        } catch (Exception ex) {
            log.error("创建课程异常,{}", ex);
            return "create course fail";
        }
        return "create course success";
    }

    @JsonAnyGetter
    @GetMapping("/obtain")
    public Object obtainAll() {
        Object result = courseOps.get(COURSE_ALL_KEY);
        if (ObjectUtils.isEmpty(result)) {
            List<Course> courseList = courseDAO.selectAll();
            if (!CollectionUtils.isEmpty(courseList)) {
                courseOps.set(COURSE_ALL_KEY, courseList);
            }
            result = courseList;
        }
        return result;
    }

    @JsonAnyGetter
    @GetMapping("/obtainOne/{id}")
    public Object obtainOne(@PathVariable String id) {
        // 去缓存中获取
        Object cacheData = courseOps.get(COURSE_ID_KEY + id);
        // 缓存中不存在时，去数据库中获取
        if (ObjectUtils.isEmpty(cacheData)) {
            Course course = courseDAO.selectOneByPrimaryKey(id);
            // 添加到缓存
            DataCacheUtil.cachedData(courseOps, COURSE_ID_KEY + id, course, 10);
            cacheData = course;
        }
        return cacheData;
    }
}
