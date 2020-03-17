package com.example.bb.redis.service;

import com.example.bb.common.dao.IStudentDAO;
import com.example.bb.common.domain.Student;
import com.example.bb.common.service.IStudentService;
import com.example.bb.redis.util.DataCacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;

/**
 * Dao的具体实现
 *
 * @author BB
 * Create: 2020/3/14 9:17
 */
@Slf4j
@Service
public class StudentServiceImpl implements IStudentService {

    private final static String ALL_KEY_PREFIX = "stu:all";
    private final static String ID_KEY_PREFIX = "stu:id:";
    private static final Student PRESENT = new Student();

    private IStudentDAO iStudentDAO;
    private ValueOperations<String, Student> studentOps;
    @Autowired
    private ValueOperations<String, List<Student>> studentListOps;

    public StudentServiceImpl(IStudentDAO iStudentDAO, ValueOperations<String, Student> studentOps) {
        this.iStudentDAO = iStudentDAO;
        this.studentOps = studentOps;
    }

    @Override
    public Student save(Student stu) {
        Student student = iStudentDAO.insert(stu);
        if (!Objects.isNull(student)) {
            // 删除列表
            DataCacheUtil.removeCacheData(studentListOps, ALL_KEY_PREFIX);

            // 缓存当前对象
            DataCacheUtil.cachedData(studentOps, buildCacheKey(stu.getStuId()), student, 30);
        }
        return student;
    }

    @Override
    public List<Student> queryAllByCache() {
        List<Student> result = studentListOps.get(ALL_KEY_PREFIX);
        // redis中不存在，则查下数据库
        if (CollectionUtils.isEmpty(result)) {
            List<Student> stuList = iStudentDAO.selectAll();
            if (!CollectionUtils.isEmpty(stuList)) {
                DataCacheUtil.cachedListData(studentListOps, ALL_KEY_PREFIX, stuList, 3600);
            }
            result = stuList;
        }
        return result;
    }

    @Override
    public List<Student> queryAllNotByCache() {
        return iStudentDAO.selectAll();
    }

    @Override
    public Student queryOneByPrimaryKey(String id) {
        // 去缓存中获取
        Student cacheData = studentOps.get(buildCacheKey(id));
        // 缓存中不存在时，去数据库中获取
        if (ObjectUtils.isEmpty(cacheData)) {
            Student student = iStudentDAO.selectOneByPrimaryKey(id);
            // 添加到缓存
            if (!ObjectUtils.isEmpty(student)) {
                cacheData = student;
            } else {
                // 防止恶意缓存穿透，可以设置一个固定的值
                cacheData = PRESENT;
            }
            DataCacheUtil.cachedData(studentOps, buildCacheKey(id), cacheData, 30);
        }
        return cacheData;
    }

    @Override
    public boolean removeByPrimaryKey(String id) {
        // 删除缓存
        DataCacheUtil.removeCacheData(studentOps, buildCacheKey(id));
        return iStudentDAO.deleteByPrimaryKey(id);
    }

    @Override
    public Student modifyByPrimaryKey(String id, String name) {
        // 删除缓存
        DataCacheUtil.removeCacheData(studentOps, buildCacheKey(id));
        return iStudentDAO.updateByPrimaryKey(id, name);
    }

    private String buildCacheKey(String id) {
        return ID_KEY_PREFIX + id;
    }
}
