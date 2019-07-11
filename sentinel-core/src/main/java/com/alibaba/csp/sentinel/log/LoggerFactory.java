package com.alibaba.csp.sentinel.log;

import com.alibaba.csp.sentinel.extension.ExtensionLoader;
import com.alibaba.csp.sentinel.log.sentinel.SentinelLoggerAdapter;
import com.alibaba.csp.sentinel.log.support.FailsafeLogger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by admin on 2019/7/9.
 */
public class LoggerFactory
{

	private static final ConcurrentMap<String, FailsafeLogger> LOGGERS = new ConcurrentHashMap<>();
	private static volatile LoggerAdapter LOGGER_ADAPTER;

	public static void setLoggerAdapter(String loggerAdapter)
	{
		if(loggerAdapter != null && loggerAdapter.length() > 0)
		{
			setLoggerAdapter(ExtensionLoader.getExtensionLoader(LoggerAdapter.class).getExtension(loggerAdapter));
		}
	}


	public static void setLoggerAdapter(LoggerAdapter loggerAdapter)
	{
		if(loggerAdapter != null)
		{
			Logger logger = loggerAdapter.getLogger(LoggerFactory.class.getName());
			logger.info("using logger: " + loggerAdapter.getClass().getName());
			LoggerFactory.LOGGER_ADAPTER = loggerAdapter;
			for(Map.Entry<String, FailsafeLogger> entry : LOGGERS.entrySet())
			{
				entry.getValue().setLogger(LOGGER_ADAPTER.getLogger(entry.getKey()));
			}
		}
	}

	public static Logger getLogger(Class<?> key)
	{
		setDefaultAdapterIfNeed();
		return LOGGERS.computeIfAbsent(key.getName(), name -> new FailsafeLogger(LOGGER_ADAPTER.getLogger(name)));
	}

	public static Logger getLogger(String key)
	{
		setDefaultAdapterIfNeed();
		return LOGGERS.computeIfAbsent(key, name -> new FailsafeLogger(LOGGER_ADAPTER.getLogger(name)));
	}


	private static void setDefaultAdapterIfNeed()
	{
		if(LOGGER_ADAPTER == null)
		{
			setLoggerAdapter(new SentinelLoggerAdapter());
		}
	}
}
