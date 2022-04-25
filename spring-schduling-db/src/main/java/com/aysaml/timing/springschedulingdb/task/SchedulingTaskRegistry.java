package com.aysaml.timing.springschedulingdb.task;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * Scheduling task registry.
 *
 * @author aysaml
 * @date 2022/4/24
 */
@Component
public class SchedulingTaskRegistry {

    private final Map<Long, SchedulingTaskFutureHolder> taskMap = new HashMap<>();

    @Resource(name = "customTaskScheduler")
    private TaskScheduler taskScheduler;

    public void addCornTask(Long taskId, Runnable task, String corn) {
        this.addCornTask(taskId, new CronTask(task, corn));
    }

    private void addCornTask(Long taskId, CronTask cronTask) {
        taskMap.put(taskId, scheduledTask(taskId, cronTask));
    }

    private SchedulingTaskFutureHolder scheduledTask(Long taskId, CronTask cronTask) {
        ScheduledFuture<?> future = this.taskScheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger());
        return new SchedulingTaskFutureHolder(future);
    }

    public void removeCornTask(Long taskId) {
        SchedulingTaskFutureHolder holder = taskMap.remove(taskId);
        if (null != holder) {
            holder.cancel();
        }
    }
}
