package com.example.bb.common.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 抽象的DAO对象，封装对数据库相关操作的公共方法，
 *
 * @author BB
 * Create: 2020/3/13 20:30
 */
public abstract class AbstractBasicDAO<E> {

    /**
     * 查询多条记录
     *
     * @param sql  查询语句
     * @param args 查询参数
     * @return 查询结果
     */
    protected List<E> query(String sql, Object... args) {
        return getJdbcTemplate().query(sql, args, this::generatorE);
    }

    /**
     * 查询单条记录
     *
     * @param sql 查询语句
     * @param args 查询参数
     * @return 查询结果
     */
    protected E queryOne(String sql, Object... args) {
        List<E> eList = query(sql, args);
        if (!CollectionUtils.isEmpty(eList)) {
            if (eList.size() > 1) {
                throw new IllegalArgumentException("期望查下结果只有一条记录，但是返回的结果集中包含多条记录!");
            }
            return eList.get(0);
        }
        return null;
    }

    /**
     * 从子类获取jdbcTemplate对象
     *
     * @return jdbc访问模板
     */
    protected abstract JdbcTemplate getJdbcTemplate();

    /**
     * 生成E对象
     *
     * @param resultSet 结果集
     * @param i
     * @return E对象
     * @throws SQLException 数据库异常
     */
    protected abstract E generatorE(ResultSet resultSet, int i) throws SQLException;
}
