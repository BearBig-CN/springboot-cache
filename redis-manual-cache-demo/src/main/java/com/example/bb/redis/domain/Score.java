package com.example.bb.redis.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分数
 *
 * @author BB
 * Create: 2020/3/13 20:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Score {
    /** 主键 */
    private int id;
    /** 学生编号 */
    private int stuId;
    /** 课程编号 */
    private int courseId;
    /** 分数 */
    private double score;
}
