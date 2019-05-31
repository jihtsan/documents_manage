package com.jsan.github.doc_manager.controller;


import com.jsan.github.doc_manager.common.BaseController;
import com.jsan.github.doc_manager.entity.RhiUser;
import com.jsan.github.doc_manager.entity.VO.ResponseModel;
import com.jsan.github.doc_manager.exception.BusinessException;
import com.jsan.github.doc_manager.service.IRhiUserService;
import com.jsan.github.doc_manager.service.Impl.RhiUserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2019-05-28
 */
@Api(tags = "犀牛-用户")
@Controller
@Slf4j
@RequestMapping("/rhi-user")
public class RhiUserController extends BaseController {


    private final IRhiUserService userService;

    public RhiUserController(IRhiUserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "创建/更新用户", httpMethod = "POST", response = String.class)
    @RequestMapping(value = "cu_user", method = RequestMethod.POST, produces = "application/json")
    public ResponseModel createOrUpdateUser(@RequestBody RhiUser user) {
        userService.saveOrUpdate(user);
        return response("登陆成功");
    }

    @ApiOperation(value = "删除用户", httpMethod = "POST", response = String.class)
    @RequestMapping(value = "d_user", method = RequestMethod.POST, produces = "application/json")
    public ResponseModel deleteUser(@RequestParam("user_id") long userid) {
        userService.removeById(userid);
        return response("登陆成功");
    }

    @ApiOperation(value = "用户列表", httpMethod = "GET", response = String.class)
    @RequestMapping(value = "r_user", method = RequestMethod.POST, produces = "application/json")
    public ResponseModel retrieveUser(@RequestParam("user_name") String userName,@RequestParam("nick_name") String nickName) {
        userService.retrieveUser(userName,nickName);
        return response("登陆成功");
    }



}
