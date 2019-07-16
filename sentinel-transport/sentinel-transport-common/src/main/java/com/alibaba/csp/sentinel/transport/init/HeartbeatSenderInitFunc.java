/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.transport.init;

import com.alibaba.csp.sentinel.config.SentinelConfig;
import com.alibaba.csp.sentinel.extension.ExtensionLoader;
import com.alibaba.csp.sentinel.heartbeat.HeartbeatSenderProvider;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.init.InitOrder;
import com.alibaba.csp.sentinel.log.RecordLog;
import com.alibaba.csp.sentinel.threadpool.ThreadPool;
import com.alibaba.csp.sentinel.transport.HeartbeatSender;
import com.alibaba.csp.sentinel.transport.config.TransportConfig;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Global init function for heartbeat sender.
 *
 * @author Eric Zhao
 */
@InitOrder(-1)
public class HeartbeatSenderInitFunc implements InitFunc
{

	private ScheduledExecutorService pool = null;

	private void initSchedulerIfNeeded()
	{
		if(pool == null)
		{
			ThreadPool threadPool = ExtensionLoader.getExtensionLoader(ThreadPool.class).getActiveExtension();
			pool = threadPool.getScheduledExecutor("sentinel-heartbeat-send-task", 2);
		}
	}

	@Override
	public void init()
	{
		HeartbeatSender sender = HeartbeatSenderProvider.getHeartbeatSender();
		if(sender == null)
		{
			RecordLog.warn("[HeartbeatSenderInitFunc] WARN: No HeartbeatSender loaded");
			return;
		}

		initSchedulerIfNeeded();

		scheduleHeartbeatTask(sender);

		RecordLog.info("[HeartbeatSenderInit] HeartbeatSender started: "
				+ sender.getClass().getCanonicalName());
	}

	private void setIntervalIfNotExists(long interval)
	{
		SentinelConfig.setConfig(TransportConfig.HEARTBEAT_INTERVAL_MS, String.valueOf(interval));
	}

	private void scheduleHeartbeatTask(/*@NonNull*/ final HeartbeatSender sender)
	{
		long interval = sender.intervalMs();

		if(interval == -1)
		{
			RecordLog.warn("[HeartbeatSender] Send heartbeat stop");
			return;
		}

		setIntervalIfNotExists(interval);

		pool.schedule(() ->
		{
			try
			{
				sender.sendHeartbeat();
			}
			catch(Throwable e)
			{
				RecordLog.warn("[HeartbeatSender] Send heartbeat error", e);
			}

			scheduleHeartbeatTask(sender);

		}, interval, TimeUnit.MILLISECONDS);

	}
}
