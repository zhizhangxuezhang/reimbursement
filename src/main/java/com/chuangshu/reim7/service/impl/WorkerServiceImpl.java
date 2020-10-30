package com.chuangshu.reim7.service.impl;

import com.chuangshu.reim7.dto.ExcelSuccessInfoDTO;
import com.chuangshu.reim7.dto.ShowWantPi;
import com.chuangshu.reim7.mapper.ReimbursementMapper;
import com.chuangshu.reim7.mapper.StudentMapper;
import com.chuangshu.reim7.mapper.WorkerMapper;
import com.chuangshu.reim7.pojo.Reimbursement;
import com.chuangshu.reim7.pojo.Student;
import com.chuangshu.reim7.pojo.Worker;
import com.chuangshu.reim7.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Guo Jiaqi
 * @version 1.0
 * @date 2020/10/4 22:47
 */
//处理职工业务
@Service
public class WorkerServiceImpl implements WorkerService {
    @Autowired
    private ReimbursementMapper reimbursementMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private WorkerMapper workerMapper;

    //审核成功
    @Override
    public void verifySuccess(Integer id,String reim_type, String reim_money) {
        Reimbursement reimbursement1 = new Reimbursement();
        reimbursement1.setId(id);
        List<Reimbursement> select = reimbursementMapper.select(reimbursement1);
        Reimbursement reimbursement2 = select.get(0);

        Reimbursement reimbursement = new Reimbursement(id,reimbursement2.getHospital(),reimbursement2.getBingli(), reimbursement2.getIsHaveProve(), reimbursement2.getChangeProve(), reimbursement2.getMoney(), reimbursement2.getBill(), reimbursement2.getTime(),reim_type,reim_money, reimbursement2.getReason(), 1, reimbursement2.getOpen_id());
        reimbursementMapper.updateByPrimaryKey(reimbursement);
    }

    //审核失败
    @Override
    public void verifyFail(Integer id,String reim_type, String reim_money, String reason) {
        Reimbursement reimbursement1 = new Reimbursement();
        reimbursement1.setId(id);
        List<Reimbursement> select = reimbursementMapper.select(reimbursement1);
        Reimbursement reimbursement2 = select.get(0);
        Reimbursement reimbursement = new Reimbursement(id,reimbursement2.getHospital(),reimbursement2.getBingli(), reimbursement2.getIsHaveProve(), reimbursement2.getChangeProve(), reimbursement2.getMoney(), reimbursement2.getBill(), reimbursement2.getTime(),reim_type,reim_money, reason, 2, reimbursement2.getOpen_id());
        reimbursementMapper.updateByPrimaryKey(reimbursement);
    }

    @Override
    public List<ShowWantPi> showWantPi() {
        Integer state = 0;
        Reimbursement reimbursement1 = new Reimbursement();
        reimbursement1.setState(state);
        List<Reimbursement> reimbursements = reimbursementMapper.select(reimbursement1);
        List<ShowWantPi> list = new ArrayList<>();
        //遍历list
        for(int i =0;i<reimbursements.size();i++){
            Reimbursement reimbursement = reimbursements.get(i);
            String open_id = reimbursement.getOpen_id();
            Student student1 = new Student();
            student1.setOpen_id(open_id);
            List<Student> students = studentMapper.select(student1);
            Student student = students.get(0);
            ShowWantPi showWantPi = new ShowWantPi(reimbursement.getId(),student.getName(),student.getCollege(),student.getMyclass(),student.getSchool_num(),reimbursement.getHospital(),reimbursement.getBingli(),reimbursement.getIsHaveProve(),reimbursement.getChangeProve(),reimbursement.getMoney(),reimbursement.getBill(),reimbursement.getTime(),reimbursement.getReim_type(),reimbursement.getReim_money(),reimbursement.getReason(),reimbursement.getState());
            list.add(i,showWantPi);
        }
        return list;
    }

    @Override
    public List<ShowWantPi> showfail() {
        Integer state = 2;
        Reimbursement reimbursement1 = new Reimbursement();
        reimbursement1.setState(state);
        List<Reimbursement> reimbursements = reimbursementMapper.select(reimbursement1);
        List<ShowWantPi> list = new ArrayList<>();
        //遍历list
        for(int i =0;i<reimbursements.size();i++){
            Reimbursement reimbursement = reimbursements.get(i);
            String open_id = reimbursement.getOpen_id();
            Student student1 = new Student();
            student1.setOpen_id(open_id);
            List<Student> students = studentMapper.select(student1);
            Student student = students.get(0);
            ShowWantPi showWantPi = new ShowWantPi(reimbursement.getId(),student.getName(),student.getCollege(),student.getMyclass(),student.getSchool_num(),reimbursement.getHospital(),reimbursement.getBingli(),reimbursement.getIsHaveProve(),reimbursement.getChangeProve(),reimbursement.getMoney(),reimbursement.getBill(),reimbursement.getTime(),reimbursement.getReim_type(),reimbursement.getReim_money(),reimbursement.getReason(),reimbursement.getState());
            list.add(i,showWantPi);
        }
        return list;
    }

    @Override
    public List<ExcelSuccessInfoDTO> importSuccess() {
        Integer state = 1;
        Reimbursement reimbursement1 = new Reimbursement();
        reimbursement1.setState(state);
        List<Reimbursement> reimbursements = reimbursementMapper.select(reimbursement1);
        List<ExcelSuccessInfoDTO> list = new ArrayList<>();
        //遍历list
        for(int i =0;i<reimbursements.size();i++){
            Reimbursement reimbursement = reimbursements.get(i);
            String open_id = reimbursement.getOpen_id();
            Student student1 = new Student();
            student1.setOpen_id(open_id);
            List<Student> students = studentMapper.select(student1);
            Student student = students.get(0);
            ExcelSuccessInfoDTO excelSuccessInfoDTO = new ExcelSuccessInfoDTO(student.getName(),student.getCollege(),student.getMyclass(),student.getSchool_num(),reimbursement.getHospital(),reimbursement.getBingli(),reimbursement.getIsHaveProve(),reimbursement.getChangeProve(),reimbursement.getMoney(),reimbursement.getBill(),reimbursement.getTime(),reimbursement.getReim_type(),reimbursement.getReim_money(),reimbursement.getReason(),state);
            list.add(i,excelSuccessInfoDTO);
        }
        System.out.println(list.toString());
        return list;
    }


    @Override
    public Worker finwor(String open_id) {
        Worker worker = new Worker();
        worker.setOpen_id(open_id);
        List<Worker> select = workerMapper.select(worker);
        Worker worker1 = select.get(0);
        return worker1;
    }
}
