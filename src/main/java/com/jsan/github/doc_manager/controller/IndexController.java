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


    @GetMapping(value = "loginhtml")
    public ModelAndView tologinhtml(HttpSession session) {
        Console.log(session);
        return new ModelAndView("Login");
    }

    @GetMapping(value = "toindex")
    public ModelAndView toindex(HttpSession session) {
        Console.log(session);
        return new ModelAndView("ProductList");
    }

    @GetMapping(value = "toUserAdd")
    public ModelAndView toUserAdd(HttpSession session) {
        return new ModelAndView("UserAdd");
    }

    @GetMapping(value = "toUserList")
    public ModelAndView toUserList(HttpSession session) {
        return new ModelAndView("UserList");
    }

    @GetMapping(value = "toNewWordList")
    public ModelAndView toNewWordList(HttpSession session) {
        return new ModelAndView("NewWordList");
    }

    @GetMapping(value = "toNewWordAdd")
    public ModelAndView toNewWordAdd(HttpSession session) {
        return new ModelAndView("NewWordAdd");
    }

    @GetMapping(value = "toNewWordUpdate")
    public ModelAndView toNewWordUpdate(HttpSession session) {
        return new ModelAndView("NewWordUpdate");
    }

    @GetMapping(value = "toProductClassifyList")
    public ModelAndView toProductClassifyList(HttpSession session) {
        return new ModelAndView("ProductClassifyList");
    }

    @GetMapping(value = "toProductList")
    public ModelAndView toProductList(HttpSession session) {
        return new ModelAndView("ProductList");
    }

    @GetMapping(value = "toProductAdd")
    public ModelAndView toProductAdd(HttpSession session) {
        return new ModelAndView("ProductAdd");
    }

    @GetMapping(value = "toProductUpdate")
    public ModelAndView toProductUpdate(HttpSession session) {
        return new ModelAndView("ProductUpdate");
    }

}
