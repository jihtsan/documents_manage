package com.jsan.github.doc_manager.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartz.*;

/**
 * @description: 定时任务DO
 * @author: Jihaotian
 * @create: 2019-03-21 11:47
 **/

@Data
@EqualsAndHashCode
public class QrtzDTO {
    private String jobName;
    private String jobGroupName;
    private TriggerKey description;
    private String jobStatus;
    private String jobTime;

    public static QrtzDTO getInstance(Scheduler sched, JobKey jobKey,Trigger trigger) throws SchedulerException {
        QrtzDTO qrtz = new QrtzDTO();
        Trigger.TriggerState triggerState = sched.getTriggerState(trigger.getKey());
        qrtz.setJobName(jobKey.getName());
        qrtz.setJobGroupName(jobKey.getGroup());
        qrtz.setDescription(trigger.getKey());
        qrtz.setJobStatus(triggerState.name());
        if (trigger instanceof CronTrigger) {
            CronTrigger cronTrigger = (CronTrigger) trigger;
            String cronExpression = cronTrigger.getCronExpression();
            qrtz.setJobTime(cronExpression);
        }
        return qrtz;
    }
}
