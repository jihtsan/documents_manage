package com.jsan.github.doc_manager.service.Impl;



import cn.hutool.core.date.DateUtil;
import com.jsan.github.doc_manager.entity.QrtzEntity;
import com.jsan.github.doc_manager.service.QuartzService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @description: quartz管理器
 * @author: Jihaotian
 * @create: 2019-02-18 14:11
 **/

@Service
@Slf4j
public class QuartzServiceImpl implements QuartzService {

    @Autowired
    private Scheduler sched;

    /**
     * 增加一个job
     *  
     *
     * @param jobClass                任务实现类
     * @param jobName                 任务名称
     * @param jobGroupName            任务组名
     * @param jobCron                 cron表达式(如：0/5 * * * * ? )
     */
    @Override
    public void addJob(Class<? extends Job> jobClass, String jobName, String jobGroupName, String jobCron) {
        try {
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName)
                    .startAt(DateBuilder.futureDate(1, IntervalUnit.SECOND))
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobCron)).startNow().build();
            sched.scheduleJob(jobDetail, trigger);
            if (!sched.isShutdown()) {
                sched.start();
            }
        } catch (SchedulerException e) {
            log.error("任务新增失败名称为:{},出错时间为:{}", jobName, DateUtil.now(), e);
        }
    }


    /**
     * 创建or更新任务，存在则更新不存在创建
     *  
     *
     * @param jobClass                任务类
     * @param jobName                 任务名称
     * @param jobGroupName            任务组名称
     * @param jobCron                 cron表达式
     */
    @Override
    public void addOrUpdateJob(Class<? extends Job> jobClass, String jobName, String jobGroupName, String jobCron) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
            if (trigger == null) {
                addJob(jobClass, jobName, jobGroupName, jobCron);
            } else {
                if (trigger.getCronExpression().equals(jobCron)) {
                    return;
                }
                updateJob(jobName, jobGroupName, jobCron);
            }
        } catch (SchedulerException e) {
            log.error("任务新增/更新失败名称为:{},出错时间为:{}", jobName, DateUtil.now(), e);
        }
    }

    /**
     *  
     *
     * @param jobClass
     * @param jobName
     * @param jobGroupName
     * @param jobTime
     */
    @Override
    public void addJob(Class<? extends Job> jobClass, String jobName, String jobGroupName, int jobTime) {
        addJob(jobClass, jobName, jobGroupName, jobTime, -1);
    }

    @Override
    public void addJob(Class<? extends Job> jobClass, String jobName, String jobGroupName, int jobTime, int jobTimes) {
        // 任务名称和组构成任务key
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName)
                .build();
        try {
            // 使用simpleTrigger规则
            Trigger trigger = null;
            if (jobTimes < 0) {
                trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName)
                        .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1).withIntervalInSeconds(jobTime))
                        .startNow().build();
            } else {
                trigger = TriggerBuilder
                        .newTrigger().withIdentity(jobName, jobGroupName).withSchedule(SimpleScheduleBuilder
                                .repeatSecondlyForever(1).withIntervalInSeconds(jobTime).withRepeatCount(jobTimes))
                        .startNow().build();
            }
            sched.scheduleJob(jobDetail, trigger);
            if (!sched.isShutdown()) {
                sched.start();
            }
        } catch (SchedulerException e) {
            log.error("任务新增失败名称为:{},出错时间为:{}", jobName, DateUtil.now(), e);
        }
    }

    @Override
    public void updateJob(String jobName, String jobGroupName, String jobTime) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobTime)).build();
            // 重启触发器
            sched.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            log.error("任务更新失败名称为:{},出错时间为:{}", jobName, DateUtil.now(), e);
        }
    }

    /**
     * 删除任务一个job
     *  
     *
     * @param jobName                 任务名称
     * @param jobGroupName            任务组名
     */
    @Override
    public void deleteJob(String jobName, String jobGroupName) {
        try {
            sched.deleteJob(new JobKey(jobName, jobGroupName));
        } catch (Exception e) {
            log.error("任务删除失败名称为:{},出错时间为:{}", jobName, DateUtil.now(), e);
        }
    }

    /**
     * 暂停一个job
     *  
     *
     * @param jobName
     * @param jobGroupName
     */
    @Override
    public void pauseJob(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            sched.pauseJob(jobKey);
        } catch (SchedulerException e) {
            log.error("任务暂停失败名称为:{},出错时间为:{}", jobName, DateUtil.now(), e);
        }
    }

    /**
     * 恢复一个job
     *  
     *
     * @param jobName
     * @param jobGroupName
     */
    @Override
    public void resumeJob(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            sched.resumeJob(jobKey);
        } catch (SchedulerException e) {
            log.error("任务恢复失败名称为:{},出错时间为:{}", jobName, DateUtil.now(), e);
        }
    }


    /**
     * 立即执行一个job
     *  
     *
     * @param jobName
     * @param jobGroupName
     */
    @Override
    public void runAJobNow(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            sched.triggerJob(jobKey);
        } catch (SchedulerException e) {
            log.error("任务执行失败名称为:{},出错时间为:{}", jobName, DateUtil.now(), e);
        }
    }


    /**
     * 获取所有计划中的任务列表
     *  
     *
     * @return
     */
    @Override
    public List<QrtzEntity> queryAllJob() {
        List<QrtzEntity> jobList = null;
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = sched.getJobKeys(matcher);
            jobList = new ArrayList<>();
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = sched.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    QrtzEntity qrtz = QrtzEntity.getInstance(sched, jobKey, trigger);
                    jobList.add(qrtz);
                }
            }
        } catch (SchedulerException e) {
            log.error("任务查询失败,出错时间为:{}", DateUtil.now(), e);
        }
        return jobList;
    }


    /**
     * 获取所有正在运行的job
     *  
     *
     * @return
     */
    @Override
    public List<QrtzEntity> queryRunJon() {
        List<QrtzEntity> jobList = null;
        try {
            List<JobExecutionContext> executingJobs = sched.getCurrentlyExecutingJobs();
            jobList = new ArrayList<>(executingJobs.size());
            for (JobExecutionContext executingJob : executingJobs) {
                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();
                QrtzEntity qrtz = QrtzEntity.getInstance(sched, jobKey, trigger);
                jobList.add(qrtz);
            }
        } catch (SchedulerException e) {
            log.error("查询正在运行任务失败,出错时间为:{}", DateUtil.now(), e);
        }
        return jobList;
    }

}
