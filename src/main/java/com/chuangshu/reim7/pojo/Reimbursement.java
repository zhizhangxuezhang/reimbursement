package com.chuangshu.reim7.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Guo Jiaqi
 * @version 1.0
 * @date 2020/10/4 22:24
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reimbursement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String hospital;
    private String bingli;
    @Column(name = "is_have_prove", columnDefinition = "bit(1) default 1")
    private Boolean isHaveProve;//是否有转诊证明
    private String changeProve;//转诊证明
    private String money;//花费金额
    private String bill;//发票
    private String time;//看病时间
    private String reim_type;//报销类型
    private String reim_money;//报销金额
    private String reason;//失败原因
    private Integer state;//状态：0：未审核；1：审核成功；2审核失败
    private String open_id;


}
