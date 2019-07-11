package com.alibaba.csp.sentinel.log.sentinel;

import com.alibaba.csp.sentinel.log.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * Created by admin on 2019/7/10.
 */
public class DefaultJdkLog implements Logger
{

	private static Map<String, DefaultJdkLog> cacheLogInstance = new HashMap<>();

	private java.util.logging.Logger logger;

	private DefaultJdkLog(String name)
	{
		logger = java.util.logging.Logger.getLogger(name);
	}

	public static DefaultJdkLog getLogger(String name)
	{
		return cacheLogInstance.computeIfAbsent(name, DefaultJdkLog::new);
	}

	@Override
	public void trace(String msg)
	{
		logger.log(Level.FINER, msg);
	}

	@Override
	public void trace(String msg, Object... args)
	{
		logger.log(Level.FINER, msg, args);
	}

	@Override
	public void trace(Throwable e)
	{
		logger.log(Level.FINER, e.getMessage(), e);
	}

	@Override
	public void trace(String msg, Throwable e)
	{
		logger.log(Level.FINER, msg, e);
	}

	@Override
	public void debug(String msg)
	{
		logger.log(Level.FINE, msg);
	}

	@Override
	public void debug(String msg, Object... args)
	{
		logger.log(Level.FINER, msg, args);
	}

	@Override
	public void debug(Throwable e)
	{
		logger.log(Level.FINE, e.getMessage(), e);
	}

	@Override
	public void debug(String msg, Throwable e)
	{
		logger.log(Level.FINE, msg, e);
	}

	@Override
	public void info(String msg)
	{
		logger.log(Level.INFO, msg);
	}

	@Override
	public void info(String msg, Object... args)
	{
		logger.log(Level.INFO, msg, args);
	}

	@Override
	public void info(String msg, Throwable e)
	{
		logger.log(Level.INFO, msg, e);
	}

	@Override
	public void warn(String msg)
	{
		logger.log(Level.WARNING, msg);
	}

	@Override
	public void warn(String msg, Object... args)
	{
		logger.log(Level.WARNING, msg, args);
	}

	@Override
	public void warn(String msg, Throwable e)
	{
		logger.log(Level.WARNING, msg, e);
	}

	@Override
	public void error(String msg)
	{
		logger.log(Level.SEVERE, msg);
	}

	@Override
	public void error(String msg, Object... args)
	{
		logger.log(Level.SEVERE, msg, args);
	}

	@Override
	public void error(String msg, Throwable e)
	{
		logger.log(Level.SEVERE, msg, e);
	}

	@Override
	public void error(Throwable e)
	{
		logger.log(Level.SEVERE, e.getMessage(), e);
	}

	@Override
	public void info(Throwable e)
	{
		logger.log(Level.INFO, e.getMessage(), e);
	}

	@Override
	public void warn(Throwable e)
	{
		logger.log(Level.WARNING, e.getMessage(), e);
	}

	@Override
	public boolean isTraceEnabled()
	{
		return logger.isLoggable(Level.FINER);
	}

	@Override
	public boolean isDebugEnabled()
	{
		return logger.isLoggable(Level.FINE);
	}

	@Override
	public boolean isInfoEnabled()
	{
		return logger.isLoggable(Level.INFO);
	}

	@Override
	public boolean isWarnEnabled()
	{
		return logger.isLoggable(Level.WARNING);
	}

	@Override
	public boolean isErrorEnabled()
	{
		return logger.isLoggable(Level.SEVERE);
	}
}
