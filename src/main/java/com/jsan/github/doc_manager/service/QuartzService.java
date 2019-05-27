package com.jsan.github.doc_manager.service;


import com.jsan.github.doc_manager.entity.QrtzEntity;
import org.quartz.*;

import java.util.*;

/**
 * @description: quartz管理器
 * @author: Jihaotian
 * @create: 2019-02-18 14:11
 **/


public interface QuartzService {

    /**
     * 增加一个job
     *  
     *
     * @param jobClass                任务实现类
     * @param jobName                 任务名称
     * @param jobGroupName            任务组名
     * @param jobCron                 cron表达式(如：0/5 * * * * ? )
     */
    void addJob(Class<? extends Job> jobClass, String jobName, String jobGroupName, String jobCron);


    /**
     * 创建or更新任务，存在则更新不存在创建
     *  
     *
     * @param jobClass                任务类
     * @param jobName                 任务名称
     * @param jobGroupName            任务组名称
     * @param jobCron                 cron表达式
     */
    void addOrUpdateJob(Class<? extends Job> jobClass, String jobName, String jobGroupName, String jobCron);

    /**
     *  
     *
     * @param jobClass
     * @param jobName
     * @param jobGroupName
     * @param jobTime
     */
    void addJob(Class<? extends Job> jobClass, String jobName, String jobGroupName, int jobTime);

    void addJob(Class<? extends Job> jobClass, String jobName, String jobGroupName, int jobTime, int jobTimes);

    void updateJob(String jobName, String jobGroupName, String jobTime);

    /**
     * 删除任务一个job
     *  
     *
     * @param jobName                 任务名称
     * @param jobGroupName            任务组名
     */
    void deleteJob(String jobName, String jobGroupName);

    /**
     * 暂停一个job
     *  
     *
     * @param jobName
     * @param jobGroupName
     */
    void pauseJob(String jobName, String jobGroupName);

    /**
     * 恢复一个job
     *  
     *
     * @param jobName
     * @param jobGroupName
     */
    void resumeJob(String jobName, String jobGroupName);


    /**
     * 立即执行一个job
     *  
     *
     * @param jobName
     * @param jobGroupName
     */
    void runAJobNow(String jobName, String jobGroupName);


    /**
     * 获取所有计划中的任务列表
     *  
     *
     * @return
     */
    List<QrtzEntity> queryAllJob();


    /**
     * 获取所有正在运行的job
     *  
     *
     * @return
     */
    List<QrtzEntity> queryRunJon();

}
