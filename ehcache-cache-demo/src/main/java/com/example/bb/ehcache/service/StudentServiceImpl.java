package com.example.bb.ehcache.service;

import com.example.bb.common.dao.IStudentDAO;
import com.example.bb.common.domain.Student;
import com.example.bb.common.service.IStudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Dao的具体实现
 *
 * @author BB
 * Create: 2020/3/14 9:17
 */
@Slf4j
@Service
public class StudentServiceImpl implements IStudentService {

    private IStudentDAO iStudentDAO;

    public StudentServiceImpl(IStudentDAO iStudentDAO) {
        this.iStudentDAO = iStudentDAO;
    }

    @Override
    @Caching(
            cacheable = {@Cacheable(cacheNames = "students", key = "#stu.stuId")},
            evict = {@CacheEvict(cacheNames = "students", key = "T(org.springframework.cache.interceptor.SimpleKey).EMPTY", beforeInvocation = true)}
    )
    public Student save(Student stu) {
        return iStudentDAO.insert(stu);
    }

    @Override
    @Cacheable(cacheNames = "students")
    public List<Student> queryAllByCache() {
        return iStudentDAO.selectAll();
    }

    @Override
    public List<Student> queryAllNotByCache() {
        return queryAllByCache();
    }

    @Override
    @Cacheable(cacheNames = "students", key = "#id")
    public Student queryOneByPrimaryKey(String id) {
        return iStudentDAO.selectOneByPrimaryKey(id);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "students", key = "#id"),
            @CacheEvict(cacheNames = "students", key = "T(org.springframework.cache.interceptor.SimpleKey).EMPTY")
    })
    public boolean removeByPrimaryKey(String id) {
        return iStudentDAO.deleteByPrimaryKey(id);
    }

    @Override
    @Caching(
            put = {@CachePut(cacheNames = "students", key = "#id")},
            evict = {@CacheEvict(cacheNames = "students", key = "T(org.springframework.cache.interceptor.SimpleKey).EMPTY")}
    )
    public Student modifyByPrimaryKey(String id, String name) {
        return iStudentDAO.updateByPrimaryKey(id, name);
    }
}
