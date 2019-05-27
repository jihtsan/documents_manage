package com.jsan.github.doc_manager.timers;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @description: 时间定时任务
 * @author: Jihaotian
 * @create: 2019-03-21 15:02
 **/
@Slf4j
public class GetDateTimer extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext)  {
        log.info("GetDateTimer:_______运行:" + DateUtil.now());
    }
}
