package com.aysaml.timing.springschedulingdb.manager;

import com.aysaml.timing.springschedulingdb.mapper.ScheduledTaskMapper;
import com.aysaml.timing.springschedulingdb.task.SchedulingTaskRegistry;
import com.aysaml.timing.springschedulingdb.task.SchedulingTaskRunnable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author wangning
 * @date 2022/4/25
 */
@Service
@AllArgsConstructor
public class ScheduledTaskManager {

    private final ScheduledTaskMapper taskMapper;

    private final SchedulingTaskRegistry taskRegistry;

    public void add() {
        //example, insert some info, get taskId.
        Long taskId = taskMapper.insert();
        taskRegistry.addCornTask(taskId, new SchedulingTaskRunnable(taskId, "beanName", "methodName"), "");
    }
}
