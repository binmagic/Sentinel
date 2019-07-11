package com.alibaba.csp.sentinel.log.support;

import com.alibaba.csp.sentinel.log.Logger;

/**
 * Created by admin on 2019/7/9.
 */
public class FailsafeLogger implements Logger
{

	private Logger logger = null;

	public FailsafeLogger(Logger logger)
	{
		this.logger = logger;
	}

	public Logger getLogger()
	{
		return logger;
	}

	public void setLogger(Logger logger)
	{
		this.logger = logger;
	}


	@Override
	public void trace(String msg)
	{
		try
		{
			this.logger.trace(msg);
		}
		catch(Throwable t)
		{

		}
	}

	@Override
	public void trace(String msg, Object... args)
	{
		try
		{
			this.logger.trace(msg, args);
		}
		catch(Throwable t1)
		{

		}
	}

	@Override
	public void trace(String msg, Throwable t)
	{
		try
		{
			this.logger.trace(msg, t);
		}
		catch(Throwable t1)
		{

		}
	}

	@Override
	public void debug(String msg)
	{
		try
		{
			this.logger.debug(msg);
		}
		catch(Throwable t)
		{

		}
	}

	@Override
	public void debug(String msg, Object... args)
	{
		try
		{
			this.logger.debug(msg, args);
		}
		catch(Throwable t1)
		{

		}
	}

	@Override
	public void debug(String msg, Throwable t)
	{
		try
		{
			this.logger.debug(msg, t);
		}
		catch(Throwable t1)
		{

		}
	}

	@Override
	public void info(String msg)
	{
		try
		{
			this.logger.info(msg);
		}
		catch(Throwable t)
		{

		}
	}

	@Override
	public void info(String msg, Object... args)
	{
		try
		{
			this.logger.info(msg, args);
		}
		catch(Throwable t1)
		{

		}
	}

	@Override
	public void info(String msg, Throwable t)
	{
		try
		{
			this.logger.info(msg, t);
		}
		catch(Throwable t1)
		{

		}
	}

	@Override
	public void warn(String msg)
	{
		try
		{
			this.logger.warn(msg);
		}
		catch(Throwable t1)
		{

		}
	}

	@Override
	public void warn(String msg, Object... args)
	{
		try
		{
			this.logger.warn(msg, args);
		}
		catch(Throwable t1)
		{

		}
	}

	@Override
	public void warn(String msg, Throwable t)
	{
		try
		{
			this.logger.warn(msg, t);
		}
		catch(Throwable t1)
		{

		}
	}

	@Override
	public void error(String msg)
	{
		try
		{
			this.logger.error(msg);
		}
		catch(Throwable t)
		{

		}
	}

	@Override
	public void error(String msg, Object... args)
	{
		try
		{
			this.logger.error(msg, args);
		}
		catch(Throwable t1)
		{

		}
	}

	@Override
	public void error(String msg, Throwable t)
	{
		try
		{
			this.logger.error(msg, t);
		}
		catch(Throwable t1)
		{

		}
	}

	@Override
	public void trace(Throwable e)
	{
		try
		{
			this.logger.trace(e);
		}
		catch(Throwable t1)
		{

		}
	}

	@Override
	public void debug(Throwable e)
	{
		try
		{
			this.logger.debug(e);
		}
		catch(Throwable t1)
		{

		}
	}

	@Override
	public void error(Throwable e)
	{
		try
		{
			this.logger.error(e);
		}
		catch(Throwable t1)
		{

		}
	}

	@Override
	public void info(Throwable e)
	{
		try
		{
			this.logger.info(e);
		}
		catch(Throwable t1)
		{

		}
	}

	@Override
	public void warn(Throwable e)
	{
		try
		{
			this.logger.warn(e);
		}
		catch(Throwable t1)
		{

		}
	}

	@Override
	public boolean isTraceEnabled()
	{

		try
		{
			return this.logger.isTraceEnabled();
		}
		catch(Throwable t)
		{
			return false;
		}
	}

	@Override
	public boolean isDebugEnabled()
	{
		try
		{
			return this.logger.isDebugEnabled();
		}
		catch(Throwable t)
		{
			return false;
		}
	}

	@Override
	public boolean isInfoEnabled()
	{
		try
		{
			return this.logger.isInfoEnabled();
		}
		catch(Throwable t)
		{
			return false;
		}
	}

	@Override
	public boolean isWarnEnabled()
	{
		try
		{
			return this.logger.isWarnEnabled();
		}
		catch(Throwable t)
		{
			return false;
		}
	}

	@Override
	public boolean isErrorEnabled()
	{
		try
		{
			return this.logger.isErrorEnabled();
		}
		catch(Throwable t)
		{
			return false;
		}
	}
}
