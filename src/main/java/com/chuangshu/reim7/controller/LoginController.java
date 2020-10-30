package com.chuangshu.reim7.controller;

import com.chuangshu.reim7.result.Result;
import com.chuangshu.reim7.result.ResultUtil;
import com.chuangshu.reim7.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @author Guo Jiaqi
 * @version 1.0
 * @date 2020/10/5 12:58
 */
@RestController
@Api(tags = {"登陆接口"})
@RequestMapping("/login")
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("/WXlogin")
    @ApiOperation(value = "微信小程序登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "code", dataType = "String", required = true, value = "code")
    })
    public Result login(String code){
        if (code==null) {
            return ResultUtil.Error("500","存在未填写的字段",null);
        }
        String open_id;
        try {
            open_id = loginService.login(code);
        }catch (Exception e){
            return ResultUtil.Error("500","登陆失败：",e.toString());
        }
        return ResultUtil.Success(open_id);
    }


}
