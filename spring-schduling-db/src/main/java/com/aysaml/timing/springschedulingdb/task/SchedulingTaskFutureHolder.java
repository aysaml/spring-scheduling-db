package com.aysaml.timing.springschedulingdb.task;

import java.util.concurrent.ScheduledFuture;

/**
 * SchedulingRunnable future holder.
 *
 * @author aysaml
 * @date 2022/4/24
 */
public class SchedulingTaskFutureHolder {

    private ScheduledFuture<?> future;

    public SchedulingTaskFutureHolder(ScheduledFuture<?> future) {
        this.future = future;
    }

    public void cancel() {
        ScheduledFuture<?> future = this.future;
        if (null != future) {
            future.cancel(true);
        }
    }
}
