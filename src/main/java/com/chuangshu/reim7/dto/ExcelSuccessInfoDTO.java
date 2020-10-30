package com.chuangshu.reim7.dto;


import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Guo Jiaqi
 * @version 1.0
 * @date 2020/10/5 17:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelSuccessInfoDTO implements Serializable{
    @Excel(name = "姓名", orderNum = "0", width = 15)
    private String name;
    @Excel(name = "学院", orderNum = "1", width = 15)
    private String college;
    @Excel(name = "班级", orderNum = "2", width = 15)
    private String myclass;
    @Excel(name = "学号", orderNum = "3", width = 15)
    private String school_num;
    @Excel(name = "医院", orderNum = "4", width = 15)
    private String hospital;
    @Excel(name = "病例", orderNum = "5", width = 15)
    private String bingli;//病例
    @Excel(name = "是否有转诊证明", orderNum = "6", width = 15)
    private Boolean isHaveProve;//是否有转诊证明
    @Excel(name = "转诊证明", orderNum = "7", width = 15)
    private String changeProve;//转诊证明
    @Excel(name = "花费金额", orderNum = "8", width = 15)
    private String money;//花费金额
    @Excel(name = "发票", orderNum = "9", width = 15)
    private String bill;//发票
    @Excel(name = "看病时间", orderNum = "10", width = 15)
    private String time;//看病时间
    @Excel(name = "报销类型", orderNum = "0", width = 15)
    private String reim_type;//报销类型
    @Excel(name = "报销金额", orderNum = "11", width = 15)
    private String reim_money;//报销金额
    @Excel(name = "失败原因", orderNum = "12", width = 15)
    private String reason;//失败原因
    private Integer state;//状态

}
