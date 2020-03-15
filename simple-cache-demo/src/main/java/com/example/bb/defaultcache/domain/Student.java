package com.example.bb.defaultcache.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author BB
 * Create: 2020/3/13 20:37
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {
    /** 学生编号 */
    private int stuId;
    /** 姓名 */
    private String name;
    /** 年龄 */
    private int age;
    /** 生日 */
    private Date birthday;
}
