package com.jsan.github.doc_manager.controller;


import com.jsan.github.doc_manager.common.BaseController;
import com.jsan.github.doc_manager.entity.VO.ResponseModel;
import com.jsan.github.doc_manager.service.IRhiUserService;
import com.jsan.github.doc_manager.service.Impl.RhiUserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

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

    @ApiOperation(value = "用户登录", httpMethod = "POST", response = String.class)
    @RequestMapping(value = "login", method = RequestMethod.POST, produces = "application/json")
    public ResponseModel login(
            HttpServletRequest request,
            @RequestParam("account") String account,
            @RequestParam("password") String password) {
        String tokenId = getTokenId(request);
        log.info("[LOGIN] 用户登录; account=" + account + "; token=" + tokenId);
        userService.login(tokenId, account, password);
        return response("登陆成功");
    }

}
