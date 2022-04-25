package com.aysaml.timing.springschedulingdb.task;

import com.alibaba.fastjson.JSON;
import com.aysaml.timing.springschedulingdb.config.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;

/**
 * Thread for executing scheduled task.
 *
 * @author aysaml
 * @date 2022/4/21
 */
@Slf4j
public class SchedulingTaskRunnable implements Runnable {

    private final Long taskId;

    private final String beanName;

    private final String methodName;

    private final String paramClassName;

    private Object targetBean;

    private Method method;

    private Object param;

    private final boolean onceRun;

    public SchedulingTaskRunnable(Long taskId, String beanName, String methodName) {
        this(taskId, beanName, methodName, null, null, true);
    }

    public SchedulingTaskRunnable(Long taskId, String beanName, String methodName, String param, String paramClassName, boolean onceRun) {
        this.taskId = taskId;
        this.beanName = beanName;
        this.methodName = methodName;
        this.paramClassName = paramClassName;
        this.onceRun = onceRun;
        init(param);
    }

    private void init(String paramJson) {
        try {
            this.targetBean = SpringContextUtil.getBean(this.beanName);
            if (StringUtils.isNotBlank(paramJson)) {
                Class<?> clazz = Class.forName(this.paramClassName);
                this.param = JSON.parseObject(paramJson, clazz);
                this.method = this.targetBean.getClass().getMethod(this.methodName, clazz);
            } else {
                this.method = this.targetBean.getClass().getMethod(this.methodName);
            }
        } catch (Exception e) {
            log.error("init param error...", e);
            throw new RuntimeException("SchedulingTaskRunnable init param error!");
        }
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        try {
            if (null == param) {
                method.invoke(targetBean);
            } else {
                method.invoke(targetBean, param);
            }
        } catch (Exception e) {
            log.error(String.format("SchedulingTask execute error : taskId：%s, bean: %s, method: %s，param: %s", taskId, beanName, methodName, param));
        } finally{
            if (onceRun) {
                // execute once, remove task.
                SpringContextUtil.getBean(SchedulingTaskRegistry.class).removeCornTask(taskId);
            }
        }
        long speed = System.currentTimeMillis() - startTime;
        log.info("SchedulingTask execute complete : taskId: {}, bean: {}, method: {}, param: {}, times: {}s", taskId, beanName, methodName, param, speed);
    }
}
