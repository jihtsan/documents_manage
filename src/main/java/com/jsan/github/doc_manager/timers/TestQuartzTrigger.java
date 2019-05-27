package com.jsan.github.doc_manager.timers;

import com.jsan.github.doc_manager.service.QuartzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Jihaotian
 * @create: 2019-02-18 14:09
 **/

@Slf4j
@Component
public class TestQuartzTrigger implements ApplicationRunner {

    @Autowired
    private QuartzService quartzManager;

    @Override
    public void run(ApplicationArguments args) {
        log.info("启动定时任务Quartz");
        try {
            //20秒执行一次
           quartzManager.addOrUpdateJob(GetDateTimer.class, "GetDateTimer", "GetDateTimerGroup", "0/2 * * * * ? *");
        } catch (Exception e) {
            log.error("启动测试testQuartz异常、异常信息:{}", e.getMessage());
        }
    }

}

