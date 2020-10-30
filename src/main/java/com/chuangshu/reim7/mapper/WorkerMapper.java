package com.chuangshu.reim7.mapper;

import com.chuangshu.reim7.pojo.Student;
import com.chuangshu.reim7.pojo.Worker;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Guo Jiaqi
 * @version 1.0
 * @date 2020/10/4 22:39
 */
@Repository
public interface WorkerMapper extends Mapper<Worker> {

}
