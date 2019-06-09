package com.jsan.github.doc_manager.controller;


import cn.hutool.core.lang.Console;
import com.jsan.github.doc_manager.common.BaseController;
import com.jsan.github.doc_manager.entity.VO.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Api(tags = "犀牛-路由")
@Controller
@Slf4j
@RequestMapping("/")
public class IndexController extends BaseController {


    @GetMapping(value = "hloginhtml")
    public ModelAndView hloginhtml(HttpSession session) {
        Console.log(session);
        return new ModelAndView("Login");
    }

    @GetMapping(value = "hindex")
    public ModelAndView hindex(HttpSession session) {
        Console.log(session);
        return new ModelAndView("UserList");
    }


    @GetMapping(value = "hUserAdd")
    public ModelAndView hUserAdd(HttpSession session) {
        return new ModelAndView("UserAdd");
    }

    @GetMapping(value = "hUserList")
    public ModelAndView hUserList(HttpSession session) {
        return new ModelAndView("UserList");
    }

    @GetMapping(value = "hNewWordAdd")
    public ModelAndView hNewWordAdd(HttpSession session) {
        return new ModelAndView("NewWordAdd");
    }

}
