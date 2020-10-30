package com.chuangshu.reim7.controller;

import com.chuangshu.reim7.dto.MyInfoDTO;
import com.chuangshu.reim7.dto.MyReimDTO;
import com.chuangshu.reim7.pojo.Reimbursement;
import com.chuangshu.reim7.result.FileUploadResult;
import com.chuangshu.reim7.result.Result;
import com.chuangshu.reim7.result.ResultUtil;
import com.chuangshu.reim7.service.StudentService;
import com.chuangshu.reim7.service.impl.FileUploadService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Guo Jiaqi
 * @version 1.0
 * @date 2020/10/5 13:52
 */
@RestController
@Api(tags = {"学生接口"})
@RequestMapping("/student")
public class StudentController {

    @Resource
    private FileUploadService fileUploadService;
    @Resource
    private StudentService studentService;
    @Autowired
    private RedisTemplate<String ,String> redisTemplate;

    @PostMapping("/changeProveFile")
    @ApiOperation(value = "上传转诊证明")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "open_id", dataType = "String", required = false, value = "open_id")
    })
    public Result changeProveFile(@ApiParam(value = "转诊证明",required = false)MultipartFile changeProveFile,String open_id){
        if (changeProveFile==null||open_id==null) {
            return ResultUtil.Error("500","存在未填写的字段",null);
        }
        try {
            FileUploadResult upload = fileUploadService.upload(changeProveFile);
            String name = upload.getName();
            redisTemplate.opsForValue().set(open_id+"1",name);
        }catch (Exception e){
            return ResultUtil.Error("500","上传转诊证明失败：",e.toString());
        }
        return ResultUtil.Success(redisTemplate.opsForValue().get(open_id+"1"));
    }
    @PostMapping("/billFile")
    @ApiOperation(value = "上传发票")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "open_id", dataType = "String", required = false, value = "open_id")
    })
    public Result billFile(@ApiParam(value = "发票",required = true)MultipartFile billFile,String open_id){
        if (billFile==null||open_id==null) {
            return ResultUtil.Error("500","存在未填写的字段",null);
        }
        try {
            FileUploadResult upload = fileUploadService.upload(billFile);
            String name = upload.getName();
            redisTemplate.opsForValue().set(open_id+"2",name);
        }catch (Exception e){
            return ResultUtil.Error("500","上传发票失败：",e.toString());
        }
        return ResultUtil.Success(redisTemplate.opsForValue().get(open_id+"2"));
    }
    @PostMapping("/bingliFile")
    @ApiOperation(value = "上传病例")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "open_id", dataType = "String", required = false, value = "open_id")
    })
    public Result bingliFile(@ApiParam(value = "病例",required = true)MultipartFile bingliFile,String open_id){
        if (bingliFile==null||open_id==null) {
            return ResultUtil.Error("500","存在未填写的字段",null);
        }
        try {
            FileUploadResult upload = fileUploadService.upload(bingliFile);
            String name = upload.getName();
            redisTemplate.opsForValue().set(open_id+"3",name);
        }catch (Exception e){
            return ResultUtil.Error("500","上传病例失败：",e.toString());
        }
        return ResultUtil.Success(redisTemplate.opsForValue().get(open_id+"3"));
    }

    //报销申请
    @PostMapping("/apply")
    @ApiOperation(value = "学生报销申请")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "hospital", dataType = "String", required = true, value = "医院"),
            @ApiImplicitParam(paramType = "query", name = "isHaveProve", dataType = "boolean", required = true, value = "是否有转诊证明"),
            @ApiImplicitParam(paramType = "query", name = "money", dataType = "String", required = true, value = "花费金额"),
            @ApiImplicitParam(paramType = "query", name = "time", dataType = "Date", required = true, value = "看病时间"),
            @ApiImplicitParam(paramType = "query", name = "open_id", dataType = "String", required = true, value = "open_id"),
    })
    public Result apply(String hospital, boolean isHaveProve, String money, Date time, String open_id){
        /*
        * 这个接口有点坑，因为转诊证明是可以不用提交的，但是我这个接口没有对其进行判断，所以也就说
        * 如果对方传过来一个false和无文件，这个时候会被赋值false，但是还是会从缓存中拿出文件然后提交
        * 但是这样是不对的，所以就是需要先 判断，如果传过来的是true，那就从缓存中拿出文件，如果传过来是
        * false，那就不要管他
        *
        * */
        if (hospital==null||money==null||time.toString()==null||open_id==null) {
            return ResultUtil.Error("500","存在未填写的字段",null);
        }
        Reimbursement reimbursement = new Reimbursement();
        Reimbursement reimbursement1;
        String changeProveUrl;
        System.out.println(isHaveProve);
        if(isHaveProve==true){
            changeProveUrl = redisTemplate.opsForValue().get(open_id+"1");
            System.out.println(changeProveUrl);
            reimbursement.setChangeProve(changeProveUrl);
        }
        try {
            String billUrl = redisTemplate.opsForValue().get(open_id+"2");
            String bingliUrl = redisTemplate.opsForValue().get(open_id+"3");

            reimbursement.setHospital(hospital);
            reimbursement.setBingli(bingliUrl);
            reimbursement.setIsHaveProve(isHaveProve);
            reimbursement.setMoney(money);
            reimbursement.setBill(billUrl);
            //转换时间格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(time);
            System.out.println("date2:"+date);

            reimbursement.setTime(date);
            reimbursement.setState(0);
            reimbursement.setOpen_id(open_id);
            System.out.println(reimbursement.toString());
            reimbursement1 = studentService.apply(reimbursement);

        }catch (Exception e){
            return ResultUtil.Error("500","提交失败：",e.toString());
        }
        return ResultUtil.Success(reimbursement1);

    }
    //我的资料
    @GetMapping("/myinfo")
    @ApiOperation(value = "我的资料")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "open_id", dataType = "String", required = true, value = "open_id")
    })
    public Result myInfo(String open_id){
        if(open_id==null){
            return ResultUtil.Error("500","存在未填写的字段",null);
        }
        MyInfoDTO myInfoDTO;
        try {
            myInfoDTO = studentService.myInfo(open_id);
        }catch (Exception e){
            return ResultUtil.Error("500","显示资料失败：",e.toString());
        }

        return ResultUtil.Success(myInfoDTO);
    }
    //我的报销
    @GetMapping("/myreim")
    @ApiOperation(value = "我的报销")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "open_id", dataType = "String", required = true, value = "open_id")
    })
    public Result myReim(String open_id){
        if (open_id==null){
            return ResultUtil.Error("500","存在未填写的字段",null);
        }
        List<MyReimDTO> myReimDTOS;
        try {
            myReimDTOS = studentService.myReim(open_id);
        }catch (Exception e){
            return ResultUtil.Error("500","显示报销失败：",e.toString());
        }
        return ResultUtil.Success(myReimDTOS);

    }

}
