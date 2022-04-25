package com.aysaml.timing.springschedulingdb.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author wangning
 * @date 2022/4/25
 */
@Mapper
public interface ScheduledTaskMapper {
    Long insert();
}
