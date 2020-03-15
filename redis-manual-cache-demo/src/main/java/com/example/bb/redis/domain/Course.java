package com.example.bb.redis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 课程
 *
 * @author BB
 * Create: 2020/3/13 20:32
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course implements Serializable {
    /** 课程编号 */
    private int id;
    /** 课程名称 */
    private String name;

}
