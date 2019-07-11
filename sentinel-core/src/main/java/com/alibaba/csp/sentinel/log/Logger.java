package com.alibaba.csp.sentinel.log;

/**
 * Created by admin on 2019/7/9.
 */
public interface Logger
{
	void trace(String msg);

	void trace(String msg, Object... args);

	void trace(String msg, Throwable t);

	void debug(String msg);

	void debug(String msg, Object... args);

	void debug(String msg, Throwable t);

	void info(String msg);

	void info(String msg, Object... args);

	void info(String msg, Throwable t);

	void warn(String msg);

	void warn(String msg, Object... args);

	void warn(String msg, Throwable t);

	void error(String msg);

	void error(String msg, Object... args);

	void error(String msg, Throwable t);

	void trace(Throwable e);

	void debug(Throwable e);

	void error(Throwable e);

	void info(Throwable e);

	void warn(Throwable e);

	boolean isTraceEnabled();

	boolean isDebugEnabled();

	boolean isInfoEnabled();

	boolean isWarnEnabled();

	boolean isErrorEnabled();
}
