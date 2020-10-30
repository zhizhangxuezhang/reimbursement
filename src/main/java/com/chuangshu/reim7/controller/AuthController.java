package com.chuangshu.reim7.controller;

import com.chuangshu.reim7.pojo.Student;
import com.chuangshu.reim7.pojo.Worker;
import com.chuangshu.reim7.result.FileUploadResult;
import com.chuangshu.reim7.result.Result;
import com.chuangshu.reim7.result.ResultUtil;
import com.chuangshu.reim7.service.AuthService;
import com.chuangshu.reim7.service.StudentService;
import com.chuangshu.reim7.service.WorkerService;
import com.chuangshu.reim7.service.impl.FileUploadService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author Guo Jiaqi
 * @version 1.0
 * @date 2020/10/5 13:14
 */
@RestController
@Api(tags = {"认证接口"})
@RequestMapping("/auth")
public class AuthController {
    @Resource
    private AuthService authService;
    @Autowired
    private FileUploadService fileUploadService;
    @Resource
    private StudentService studentService;
    @Resource
    private WorkerService workerService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostMapping("/bodycard_z")
    @ApiOperation(value = "身份证正面")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "open_id", dataType = "String", required = true, value = "open_id")
    })
    public Result bodyCard_z(@ApiParam(value = "身份证正面",required = true) MultipartFile bodyCard_z,String open_id){
        //判断参数是否为空
        if (bodyCard_z==null) {
            return ResultUtil.Error("500","存在未填写的字段",null);
        }
        System.out.println();
        try {
            FileUploadResult upload = fileUploadService.upload(bodyCard_z);
            String name = upload.getName();
            System.out.println(name);
            redisTemplate.opsForValue().set(open_id+"4",name);
        }catch (Exception e){
            return ResultUtil.Error("500","提交身份证正面失败",e.toString());
        }
        System.out.println(redisTemplate.opsForValue().get(open_id)+"4");
        return ResultUtil.Success(redisTemplate.opsForValue().get(open_id)+"4");

    }
    @PostMapping("/bodycard_f")
    @ApiOperation(value = "身份证反面")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "open_id", dataType = "String", required = true, value = "open_id")
    })
    public Result bodyCard_f(@ApiParam(value = "身份证反面",required = true) MultipartFile bodyCard_f,String open_id){
        //判断参数是否为空
        if (bodyCard_f==null) {
            return ResultUtil.Error("500","存在未填写的字段",null);
        }
        System.out.println();
        try {
            FileUploadResult upload = fileUploadService.upload(bodyCard_f);
            String name = upload.getName();
            System.out.println(name);
            redisTemplate.opsForValue().set(open_id+"5",name);
        }catch (Exception e){
            return ResultUtil.Error("500","提交身份证正面失败",e.toString());
        }
        System.out.println(redisTemplate.opsForValue().get(open_id+"5"));
        return ResultUtil.Success(redisTemplate.opsForValue().get(open_id)+"5");

    }


    @PostMapping("/student")
    @ApiOperation(value = "学生认证")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "open_id", dataType = "String", required = true, value = "open_id"),
            @ApiImplicitParam(paramType = "query", name = "name", dataType = "String", required = true, value = "姓名"),
            @ApiImplicitParam(paramType = "query", name = "college", dataType = "String", required = true, value = "学院"),
            @ApiImplicitParam(paramType = "query", name = "myclass", dataType = "String", required = true, value = "班级"),
            @ApiImplicitParam(paramType = "query", name = "school_num", dataType = "String", required = true, value = "学号")

    })
    public Result Auth4student(String open_id, String name, String college, String myclass, String school_num){
        //判断参数是否为空
        if (open_id==null||name==null||college==null||myclass==null||school_num==null) {
            return ResultUtil.Error("500","存在未填写的字段",null);
        }
        System.out.println(authService.selstu(open_id)==null);
        if(authService.selstu(open_id).isEmpty()){
            //该用户还没认证
            try {

                String z_url = redisTemplate.opsForValue().get(open_id+"4");
                String f_url = redisTemplate.opsForValue().get(open_id+"5");
                Student student = new Student(null,open_id,name,college,myclass,school_num,z_url,f_url);
                System.out.println(student+"ffffffff");
                authService.studentAuth(student);
            }catch (Exception e){
                return ResultUtil.Error("500","提交学生认证信息出现错误：",e.toString());
            }
            return ResultUtil.Success(null);
        }else{
            //这个用户曾经认证过，此时为更新之前的数据。
            try {
                String z_url = redisTemplate.opsForValue().get(open_id+"4");
                String f_url = redisTemplate.opsForValue().get(open_id+"5");
                Student student = new Student(null,open_id,name,college,myclass,school_num,z_url,f_url);
                System.out.println(student);
                authService.updstu(student,open_id);
            }catch (Exception e){
                return ResultUtil.Error("500","提交学生认证信息出现错误",e.toString());
            }
            return ResultUtil.Success(null);
        }

    }
    @PostMapping("/worker")
    @ApiOperation(value = "职工认证")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "open_id", dataType = "String", required = true, value = "open_id"),
            @ApiImplicitParam(paramType = "query", name = "name", dataType = "String", required = true, value = "姓名"),
            @ApiImplicitParam(paramType = "query", name = "position", dataType = "String", required = true, value = "职位")

    })
    public Result Auth4worker(String open_id, String name, String position){
        if (open_id==null||name==null||position==null) {
            return ResultUtil.Error("500","存在未填写的字段",null);
        }
        if(authService.selwor(open_id).isEmpty()){
            try {
                String z_url = redisTemplate.opsForValue().get(open_id+"4");
                String f_url = redisTemplate.opsForValue().get(open_id + "5");
                Worker worker = new Worker(null,open_id,name,position,z_url,f_url);
                authService.workerAuth(worker);
            }catch (Exception e){
                return ResultUtil.Error("500","提交职工信息出现错误：",e.toString());
            }
            return ResultUtil.Success(null);
        }else {
            try {
                String z_url = redisTemplate.opsForValue().get(open_id+"4");
                String f_url = redisTemplate.opsForValue().get(open_id + "5");
                Worker worker = new Worker(null,open_id,name,position,z_url,f_url);
                authService.updwor(worker,open_id);
            }catch (Exception e){
                return ResultUtil.Error("500","提交职工信息出现错误",e.toString());
            }
            return ResultUtil.Success(null);
        }
    }


}
