package com.chuangshu.reim7.service.impl;

import com.chuangshu.reim7.service.LoginService;
import com.chuangshu.reim7.util.Http;
import com.chuangshu.reim7.util.JsonUtils;
import com.chuangshu.reim7.wx.WXSessionModel;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Guo Jiaqi
 * @version 1.0
 * @date 2020/10/4 22:46
 */
//处理登陆业务
@Service
public class LoginServiceImpl implements LoginService {

    @Override
    public String login(String code) {
        //微信获取openid和sessionkey的url
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        //map集合存放信息
        Map<String, String> param = new HashMap<>();
        param.put("appid", "xxx");
        param.put("secret", "xxxx");
        param.put("js_code", code);
        param.put("grant_type", "authorization_code");
        //调用自定义的doget方法，返回一个带有openid和sessionkey的string类型的结果
        String wxResult = Http.doGet(url, param);
        //将结果转化为model的形式--格式转换
        WXSessionModel model = JsonUtils.jsonToPojo(wxResult, WXSessionModel.class);
        //获取open_id
        String open_id = model.getOpenid();
        return open_id;
    }
}
