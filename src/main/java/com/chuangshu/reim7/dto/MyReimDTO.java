package com.chuangshu.reim7.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Guo Jiaqi
 * @version 1.0
 * @date 2020/10/4 23:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyReimDTO implements Serializable {
    //名字
    private String name;
    //学院
    private String college;
//    班级
    private String myclass;
//    学号
    private String school_num;
//    医院
    private String hospital;
    private String bingli;
    private Boolean isHaveProve;//是否有转诊证明
    private String changeProve;//转诊证明
    private String money;
    private String time;
    private String bill;//发票
    private Integer state;
    private String reim_type;//报销类型
    private String reim_money;//报销金额
    private String reason;//失败原因
}
