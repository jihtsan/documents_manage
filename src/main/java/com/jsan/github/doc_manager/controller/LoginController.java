package com.jsan.github.doc_manager.controller;

import com.jsan.github.doc_manager.common.BaseController;
import com.jsan.github.doc_manager.entity.RhiUser;
import com.jsan.github.doc_manager.entity.VO.ResponseModel;
import com.jsan.github.doc_manager.service.IRhiUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Api(tags = "犀牛-登录")
@Controller
@Slf4j
@RequestMapping("/user")
public class LoginController extends BaseController {
    private final IRhiUserService userService;

    public LoginController(IRhiUserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "用户登录", httpMethod = "POST", response = String.class)
    @RequestMapping(value = "login", method = RequestMethod.POST, produces = "application/json")
    public ResponseModel login(
            @RequestParam("account") String account,
            @RequestParam("password") String password) {
        return response(userService.login(account, password));
    }



    @ApiOperation(value = "获取用户信息", httpMethod = "POST", response = String.class)
    @RequestMapping(value = "getUserInfo", method = RequestMethod.POST, produces = "application/json")
    public ResponseModel getUserInfo() {
        Subject currentUser = SecurityUtils.getSubject();
        Object principal = currentUser.getPrincipal();
        return response("登陆成功");
    }

    @ApiOperation("用户登出")
    @GetMapping("/logout")
    public ResponseModel logout()
    {
        Subject currentUser = SecurityUtils.getSubject();
        Object principal = currentUser.getPrincipal();
        if (principal != null) {
            currentUser.logout();
        }
        return response("登出成功");
    }


}
