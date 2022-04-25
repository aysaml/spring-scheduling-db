package com.aysaml.timing.springschedulingdb.controller;

import com.aysaml.timing.springschedulingdb.manager.ScheduledTaskManager;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author aysaml
 * @date 2022/4/25
 */
@RequestMapping("/api")
@RestController
@AllArgsConstructor
public class ScheduledTaskController {

    private final ScheduledTaskManager taskManager;

    @PostMapping("/task")
    public void addTask() {
        taskManager.add();
    }
}
