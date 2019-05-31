package com.jsan.github.doc_manager;

import cn.hutool.core.lang.Console;
import com.jsan.github.doc_manager.entity.RhiUser;
import com.jsan.github.doc_manager.service.IRhiUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DocManagerApplicationTests {
    @Resource
    private IRhiUserService userService;

    @Test
    public void contextLoads() {
    }



}
