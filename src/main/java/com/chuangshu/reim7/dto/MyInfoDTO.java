package com.chuangshu.reim7.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Guo Jiaqi
 * @version 1.0
 * @date 2020/10/4 23:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyInfoDTO implements Serializable {
    private String name;
    private String college;
    private String myclass;
    private String school_num;
}
