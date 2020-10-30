package com.chuangshu.reim7.service;

import com.chuangshu.reim7.pojo.Student;
import com.chuangshu.reim7.pojo.Worker;

import java.util.List;

/**
 * @author Guo Jiaqi
 * @version 1.0
 * @date 2020/10/4 22:45
 */
public interface AuthService {
    //学生认证
    void studentAuth(Student student);
    //职工认证
    void workerAuth(Worker worker);
    void updwor(Worker worker,String open_id);
    void updstu(Student student,String open_id);
    List<Worker> selwor(String open_id);
    List<Student> selstu(String open_id);



}
