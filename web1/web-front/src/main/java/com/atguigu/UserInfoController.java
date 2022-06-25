package com.atguigu;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.UserInfo;
import com.atguigu.entity.bo.LoginBo;
import com.atguigu.entity.bo.RegisterBo;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.service.UserInfoService;
import com.atguigu.util.MD5;
import org.springframework.beans.BeanUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Reference
    private UserInfoService userInfoService;

    @GetMapping("/sendCode/{phone}")
    public Result sendCode(@PathVariable("phone") String phone, HttpSession session) {
        String code = "1111";
        session.setAttribute("CODE", code);

        return Result.ok();

    }

    @PostMapping("/register")
    public Result register(@RequestBody RegisterBo registerBo, HttpSession session) {
        String code = (String) session.getAttribute("CODE");
        if (!code.equalsIgnoreCase(registerBo.getCode())) {
            return Result.build(null, ResultCodeEnum.CODE_ERROR);
        }
        if (userInfoService.getByPhone(registerBo.getPhone()) != null) {

            return Result.build(null, ResultCodeEnum.PHONE_REGISTER_ERROR);
        }

        UserInfo userInfo = new UserInfo();
        //将一个对象中的数据付给另一个对象
        BeanUtils.copyProperties(registerBo, userInfo);
        userInfo.setStatus(1);
        userInfo.setPassword(MD5.encrypt(userInfo.getPassword()));

        userInfoService.insert(userInfo);

        return Result.ok();

    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginBo loginBo, HttpSession session) {
        UserInfo userInfo = userInfoService.getByPhone(loginBo.getPhone());

        if (userInfo == null) {
            return Result.build(null, ResultCodeEnum.ACCOUNT_ERROR);
        }
        if (!userInfo.getPassword().equals(MD5.encrypt(loginBo.getPassword()))) {
            return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);

        }
        if (userInfo.getStatus() == 0) {
            return Result.build(null, ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }

        session.setAttribute("USER",userInfo);

        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("phone",userInfo.getPhone());
        responseMap.put("nickName",userInfo.getNickName());

        return Result.ok(responseMap);
    }
    @GetMapping("/logout")
    public Result logout(HttpSession session){
        //退出登录其实是清空session中的数据
        session.invalidate();

        return Result.ok();
    }
}
