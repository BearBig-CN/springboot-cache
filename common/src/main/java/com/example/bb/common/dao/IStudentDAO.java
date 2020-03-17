package com.example.bb.common.dao;

import com.example.bb.common.domain.Student;

import java.util.List;
import java.util.Optional;

/**
 * Dao层接口
 *
 * @author BB
 * Create: 2020/3/14 0014 17:29
 */
public interface IStudentDAO {

    /** 插入
     * @param stu 要插入的记录
     * @return 插入成功的记录
     */
    Student insert(Student stu);

    /**
     * 查下所有，会先去取缓存，缓存没有再走数据库
     * @return 结果集
     */
    List<Student> selectAll();

    /**
     * 根据主键查询
     * @param id 主键值
     * @return 单条记录
     */
    Student selectOneByPrimaryKey(String id);

    /**
     * 根据主键删除
     * @param id 主键值
     * @return true:删除成功, false:删除失败
     */
    boolean deleteByPrimaryKey(String id);

    /**
     * 根据主键更新name
     * @param id 主键值
     * @param name 新的name值
     * @return 更新后的对象
     */
    Student updateByPrimaryKey(String id, String name);
}
