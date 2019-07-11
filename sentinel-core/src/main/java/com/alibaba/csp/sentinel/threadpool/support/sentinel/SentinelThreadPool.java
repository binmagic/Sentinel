package com.alibaba.csp.sentinel.threadpool.support.sentinel;

import com.alibaba.csp.sentinel.concurrent.NamedThreadFactory;
import com.alibaba.csp.sentinel.threadpool.ThreadPool;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Created by admin on 2019/7/10.
 */
public class SentinelThreadPool implements ThreadPool
{
	@Override
	public ScheduledExecutorService getScheduledExecutor(String threadName, int threadCount)
	{
		return new ScheduledThreadPoolExecutor(threadCount, new NamedThreadFactory(threadName));
	}
}
