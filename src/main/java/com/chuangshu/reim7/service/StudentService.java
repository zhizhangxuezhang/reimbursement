package com.chuangshu.reim7.service;


import com.chuangshu.reim7.dto.MyInfoDTO;
import com.chuangshu.reim7.dto.MyReimDTO;
import com.chuangshu.reim7.pojo.Reimbursement;

import java.util.List;

/**
 * @author Guo Jiaqi
 * @version 1.0
 * @date 2020/10/4 22:45
 */
public interface StudentService {
    //报销申请
    Reimbursement apply(Reimbursement reimbursement);
    //我的资料
    MyInfoDTO myInfo(String open_id);
    //我的报销
    List<MyReimDTO> myReim(String open_id);

}
