package com.chuangshu.reim7.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Guo Jiaqi
 * @version 1.0
 * @date 2020/10/4 22:07
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Worker implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String open_id;
    private String name;
    private String position;//职位
    private String bodyCard_z;
    private String bodyCard_f;
}
