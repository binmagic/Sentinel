package com.alibaba.csp.sentinel.log.sentinel;

import com.alibaba.csp.sentinel.log.LogBase;
import com.alibaba.csp.sentinel.log.Logger;

import java.util.logging.Handler;
import java.util.logging.Level;

/**
 * Created by admin on 2019/7/10.
 */
public class CommandCenterLog extends LogBase implements Logger
{
	private static final java.util.logging.Logger heliumRecordLog = java.util.logging.Logger.getLogger("cspCommandCenterLog");
	private static final String FILE_NAME = "command-center.log";
	private static Handler logHandler = null;

	static
	{
		logHandler = makeLogger(FILE_NAME, heliumRecordLog);
	}

	private static final CommandCenterLog instance = new CommandCenterLog();


	public static CommandCenterLog getInstance()
	{
		return instance;
	}


	@Override
	public void trace(String msg)
	{
		log(heliumRecordLog, logHandler, Level.FINE, msg);
	}

	@Override
	public void trace(String msg, Object... args)
	{
		log(heliumRecordLog, logHandler, Level.FINE, msg, args);
	}

	@Override
	public void trace(String msg, Throwable t)
	{
		log(heliumRecordLog, logHandler, Level.FINE, msg, t);
	}

	@Override
	public void debug(String msg)
	{
		log(heliumRecordLog, logHandler, Level.FINE, msg);
	}

	@Override
	public void debug(String msg, Object... args)
	{
		log(heliumRecordLog, logHandler, Level.FINE, msg, args);
	}

	@Override
	public void debug(String msg, Throwable t)
	{
		log(heliumRecordLog, logHandler, Level.FINE, msg);
	}

	@Override
	public void info(String msg)
	{
		log(heliumRecordLog, logHandler, Level.INFO, msg);
	}

	@Override
	public void info(String msg, Object... args)
	{
		log(heliumRecordLog, logHandler, Level.INFO, msg, args);
	}

	@Override
	public void info(String msg, Throwable t)
	{
		log(heliumRecordLog, logHandler, Level.INFO, msg, t);
	}


	@Override
	public void warn(String msg)
	{
		log(heliumRecordLog, logHandler, Level.WARNING, msg);
	}

	@Override
	public void warn(String msg, Object... args)
	{
		log(heliumRecordLog, logHandler, Level.WARNING, msg, args);
	}

	@Override
	public void warn(String msg, Throwable t)
	{
		log(heliumRecordLog, logHandler, Level.WARNING, msg, t);
	}

	@Override
	public void error(String msg)
	{
		log(heliumRecordLog, logHandler, Level.SEVERE, msg);
	}

	@Override
	public void error(String msg, Object... args)
	{
		log(heliumRecordLog, logHandler, Level.SEVERE, msg, args);
	}

	@Override
	public void error(String msg, Throwable t)
	{
		log(heliumRecordLog, logHandler, Level.SEVERE, msg, t);
	}

	@Override
	public void trace(Throwable e)
	{

	}

	@Override
	public void debug(Throwable e)
	{

	}

	@Override
	public void error(Throwable e)
	{

	}

	@Override
	public void info(Throwable e)
	{

	}

	@Override
	public void warn(Throwable e)
	{

	}

	@Override
	public boolean isTraceEnabled()
	{
		return heliumRecordLog.isLoggable(Level.FINER);
	}

	@Override
	public boolean isDebugEnabled()
	{
		return heliumRecordLog.isLoggable(Level.FINE);
	}

	@Override
	public boolean isInfoEnabled()
	{
		return heliumRecordLog.isLoggable(Level.INFO);
	}

	@Override
	public boolean isWarnEnabled()
	{
		return heliumRecordLog.isLoggable(Level.WARNING);
	}

	@Override
	public boolean isErrorEnabled()
	{
		return heliumRecordLog.isLoggable(Level.SEVERE);
	}

}
