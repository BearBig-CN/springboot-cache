package com.example.bb.common.service;

import com.example.bb.common.domain.Student;

import java.util.List;

/**
 * @author BB
 * Create: 2020/3/16 0016 22:12
 */
public interface IStudentService {

    /**
     * 插入
     *
     * @param stu 要插入的记录
     * @return 插入成功的记录
     */
    Student save(Student stu);

    /**
     * 查下所有，会先去取缓存，缓存没有再走数据库
     *
     * @return 结果集
     */
    List<Student> queryAllByCache();

    /**
     * 查下所有，不走缓存，每次都去数据库获取
     * @return 结果集
     */
    List<Student> queryAllNotByCache();

    /**
     * 根据主键查询
     *
     * @param id 主键值
     * @return 单条记录
     */
    Student queryOneByPrimaryKey(String id);

    /**
     * 根据主键删除
     *
     * @param id 主键值
     * @return true:删除成功, false:删除失败
     */
    boolean removeByPrimaryKey(String id);

    /**
     * 根据主键更新name
     *
     * @param id   主键值
     * @param name 新的name值
     * @return 更新后的对象
     */
    Student modifyByPrimaryKey(String id, String name);

}
