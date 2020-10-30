package com.chuangshu.reim7.service.impl;

import com.chuangshu.reim7.dto.MyInfoDTO;
import com.chuangshu.reim7.dto.MyReimDTO;
import com.chuangshu.reim7.mapper.ReimbursementMapper;
import com.chuangshu.reim7.mapper.StudentMapper;
import com.chuangshu.reim7.pojo.Reimbursement;
import com.chuangshu.reim7.pojo.Student;
import com.chuangshu.reim7.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Guo Jiaqi
 * @version 1.0
 * @date 2020/10/4 22:46
 */
//处理学生业务
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private ReimbursementMapper reimbursementMapper;
    @Autowired
    private StudentMapper studentMapper;


    @Override
    public Reimbursement apply(Reimbursement reimbursement) {
        reimbursementMapper.insert(reimbursement);
        List<Reimbursement> select = reimbursementMapper.select(reimbursement);
        Reimbursement reimbursement1 = select.get(0);
        return reimbursement1;
    }

    @Override
    public MyInfoDTO myInfo(String open_id) {
        Student student1 = new Student();
        student1.setOpen_id(open_id);
        List<Student> students = studentMapper.select(student1);
        Student student = students.get(0);
        MyInfoDTO myInfoDTO = new MyInfoDTO(student.getName(),student.getCollege(),student.getMyclass(),student.getSchool_num());
        return myInfoDTO;
    }

    @Override
    public List<MyReimDTO> myReim(String open_id) {
        Student student1 = new Student();
        student1.setOpen_id(open_id);
        List<Student> students = studentMapper.select(student1);
        Student student = students.get(0);
        Reimbursement reimbursement = new Reimbursement();
        reimbursement.setOpen_id(open_id);

        System.out.println(reimbursement.toString());
        List<Reimbursement> reimbursements = reimbursementMapper.select(reimbursement);

        System.out.println(reimbursements.toString());

        List<MyReimDTO> list = new ArrayList<>();
        System.out.println(reimbursements.size());
        for(int i=0;i<reimbursements.size();i++){
            MyReimDTO myReimDTO = new MyReimDTO(student.getName(),student.getCollege(),student.getMyclass(),student.getSchool_num(),reimbursements.get(i).getHospital(),reimbursements.get(i).getBingli(),reimbursements.get(i).getIsHaveProve(),reimbursements.get(i).getChangeProve(),reimbursements.get(i).getMoney(),reimbursements.get(i).getTime(),reimbursements.get(i).getBill(),reimbursements.get(i).getState(),reimbursements.get(i).getReim_type(),reimbursements.get(i).getReim_money(),reimbursements.get(i).getReason());
            list.add(i,myReimDTO);
        }
        return list;
    }
}
