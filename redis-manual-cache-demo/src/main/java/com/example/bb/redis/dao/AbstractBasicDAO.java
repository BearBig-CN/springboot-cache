package com.example.bb.redis.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author BB
 * Create: 2020/3/13 20:30
 */
public abstract class AbstractBasicDAO<E> {

    protected List<E> query(String sql, Object... args) {
        return getJdbcTemplate().query(sql, args, this::generatorE);
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
