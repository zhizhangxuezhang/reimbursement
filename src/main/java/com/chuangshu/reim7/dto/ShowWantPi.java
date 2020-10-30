package com.chuangshu.reim7.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Guo Jiaqi
 * @version 1.0
 * @date 2020/10/5 12:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowWantPi implements Serializable {
    private Integer id;//报销的id
    private String name;
    private String college;
    private String myclass;
    private String school_num;
    private String hospital;
    private String bingli;//病例
    private Boolean isHaveProve;//是否有转诊证明
    private String changeProve;//转诊证明
    private String money;//花费金额
    private String bill;//发票
    private String time;//看病时间
    private String reim_type;//报销类型
    private String reim_money;//报销金额
    private String reason;//失败原因
    private Integer state;//状态：0：未审核；1：审核成功；2审核失败
}
