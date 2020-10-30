package com.chuangshu.reim7.controller;

import com.chuangshu.reim7.dto.ExcelSuccessInfoDTO;
import com.chuangshu.reim7.dto.ShowWantPi;
import com.chuangshu.reim7.pojo.Worker;
import com.chuangshu.reim7.result.Result;
import com.chuangshu.reim7.result.ResultUtil;
import com.chuangshu.reim7.service.WorkerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Guo Jiaqi
 * @version 1.0
 * @date 2020/10/5 16:29
 */
@RestController
@Api(tags = {"职工接口"})
@RequestMapping("/worker")
public class WorkerController {

    @Resource
    private WorkerService workerService;

    @PutMapping("/verifysuccess")
    @ApiOperation(value = "审核成功")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", dataType = "Integer", required = true, value = "主键id"),
            @ApiImplicitParam(paramType = "query", name = "reim_type", dataType = "String", required = true, value = "报销类型"),
            @ApiImplicitParam(paramType = "query", name = "reim_money", dataType = "String", required = true, value = "报销金额")
    })
    //1.审核成功
    public Result verifySuccess(Integer id, String reim_type, String reim_money){
        if(id==null||reim_money==null||reim_type==null){
            return ResultUtil.Error("500","存在未填写的字段",null);
        }
        try {
            workerService.verifySuccess(id,reim_type,reim_money);
        }catch (Exception e){
            return ResultUtil.Error("500","提交失败：",e.toString());
        }
        return ResultUtil.Success(null);

    }
    @PutMapping("/verifyfail")
    @ApiOperation(value = "审核失败")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", dataType = "Integer", required = true, value = "主键id"),
            @ApiImplicitParam(paramType = "query", name = "reim_type", dataType = "String", required = true, value = "报销类型"),
            @ApiImplicitParam(paramType = "query", name = "reim_money", dataType = "String", required = true, value = "报销金额"),
            @ApiImplicitParam(paramType = "query", name = "reason", dataType = "String", required = true, value = "报销失败原因"),
    })
    //2.审核失败
    public Result verifyFail(Integer id,String reim_type, String reim_money, String reason){
        if(id==null||reim_money==null||reim_type==null||reason==null){
            return ResultUtil.Error("500","存在未填写的字段",null);
        }
        try {
            workerService.verifyFail(id,reim_type,reim_money,reason);
        }catch (Exception e){
            return ResultUtil.Error("500","提交错误：",e.toString());
        }
        return ResultUtil.Success(null);
    }

    //显示待批
    @GetMapping("/showwantpi")
    @ApiOperation(value = "显示待批")
    public Result showWantPi(){

        List<ShowWantPi> list;
        try {
            list = workerService.showWantPi();
        }catch (Exception e){
            return ResultUtil.Error("500","未能显示待批的记录：",e.toString());
        }
        return ResultUtil.Success(list);
    }
    //显示审核失败
    @GetMapping("/showfail")
    @ApiOperation(value = "显示审核失败")
    public Result showfail(){
        List<ShowWantPi> showfail;
        try {
            showfail = workerService.showfail();
        }catch (Exception e){
            return ResultUtil.Error("500","未能显示审核失败的记录：",e.toString());
        }
        return ResultUtil.Success(showfail);
    }
    @GetMapping("/importsuccess")
    @ApiOperation(value = "显示审核成功")
    //导出成功名单
    public Result importSuccess(HttpServletResponse response) throws Exception {
        List<ExcelSuccessInfoDTO> list;
        try {
            list=workerService.importSuccess();
        }catch (Exception e){
            return ResultUtil.Error("500","显示审核通过记录发生错误",e.toString());
        }
        return ResultUtil.Success(list);
    }
    @GetMapping("/workerinfo")
    @ApiOperation(value = "职工信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "open_id", dataType = "String", required = true, value = "open_id")
    })
    //显示职工的信息
    public Result workerInfo(String open_id){
        if(open_id==null){
            return ResultUtil.Error("500","存在未填写的字段",null);
        }
        Worker worker;
        try {
            worker = workerService.finwor(open_id);
        }catch (Exception e){
            return ResultUtil.Error("500","职工信息显示失败",e.toString());
        }
        return ResultUtil.Success(worker);


    }
}
