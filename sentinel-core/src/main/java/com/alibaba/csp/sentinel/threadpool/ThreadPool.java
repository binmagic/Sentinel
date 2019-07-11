package com.alibaba.csp.sentinel.threadpool;

import com.alibaba.csp.sentinel.extension.SPI;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by admin on 2019/7/10.
 */
@SPI("sentinel")
public interface ThreadPool
{
	ScheduledExecutorService getScheduledExecutor(String threadName, int threadCount);
}
