package com.chuangshu.reim7.service;

import com.chuangshu.reim7.dto.ExcelSuccessInfoDTO;
import com.chuangshu.reim7.dto.ShowWantPi;
import com.chuangshu.reim7.pojo.Worker;

import java.util.List;

/**
 * @author Guo Jiaqi
 * @version 1.0
 * @date 2020/10/4 22:45
 */
public interface WorkerService {

    //报销审核
    //1.审核成功
    void verifySuccess(Integer id,String reim_type,String reim_money);
    //2.审核失败
    void verifyFail(Integer id,String reim_type, String reim_money, String reason);
    //显示待批
    List<ShowWantPi> showWantPi();
    //显示审核失败
    List<ShowWantPi> showfail();
    //导出成功名单
    List<ExcelSuccessInfoDTO> importSuccess();
    //查询职工的信息
    Worker finwor(String open_id);

}
