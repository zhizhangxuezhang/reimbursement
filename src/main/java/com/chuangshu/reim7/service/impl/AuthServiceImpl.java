package com.chuangshu.reim7.service.impl;

import com.chuangshu.reim7.mapper.StudentMapper;
import com.chuangshu.reim7.mapper.WorkerMapper;
import com.chuangshu.reim7.pojo.Student;
import com.chuangshu.reim7.pojo.Worker;
import com.chuangshu.reim7.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Guo Jiaqi
 * @version 1.0
 * @date 2020/10/4 22:46
 */
//处理认证业务
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private WorkerMapper workerMapper;

    @Override
    public void studentAuth(Student student) {
        studentMapper.insert(student);
    }

    @Override
    public void workerAuth(Worker worker) {
        workerMapper.insert(worker);
    }

    @Override
    public void updstu(Student student,String open_id) {
        Student student1 = new Student();
        student1.setOpen_id(open_id);
        List<Student> select = studentMapper.select(student1);
        Student student2 = select.get(0);
        student.setId(student2.getId());
        studentMapper.updateByPrimaryKey(student);
    }
    @Override
    public void updwor(Worker worker,String open_id) {
        Worker worker1 = new Worker();
        worker1.setOpen_id(open_id);
        List<Worker> select = workerMapper.select(worker1);
        Worker worker2 = select.get(0);
        worker.setId(worker2.getId());
        workerMapper.updateByPrimaryKey(worker);
    }
    @Override
    public List<Worker> selwor(String open_id){
        Worker worker = new Worker();
        worker.setOpen_id(open_id);
        List<Worker> select = workerMapper.select(worker);
        return select;
    }
    @Override
    public List<Student> selstu(String open_id){
        Student student = new Student();
        student.setOpen_id(open_id);
        List<Student> select = studentMapper.select(student);
        return select;
    }

}
