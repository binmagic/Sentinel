package com.alibaba.csp.sentinel.log.sentinel;

import com.alibaba.csp.sentinel.log.Logger;
import com.alibaba.csp.sentinel.log.LoggerAdapter;

/**
 * Created by admin on 2019/7/10.
 */
public class SentinelLoggerAdapter implements LoggerAdapter
{

	public static final String CSP_COMMAND_CENTER_LOG = "cspCommandCenterLog";
	public static final String CSP_RECORD_LOG = "cspRecordLog";

	@Override
	public Logger getLogger(String name)
	{
		if(name.equals(CSP_COMMAND_CENTER_LOG))
		{
			return CommandCenterLog.getInstance();
		}
		else if(name.equals(CSP_RECORD_LOG))
		{
			return RecordLog.getInstance();
		}

		return DefaultJdkLog.getLogger(name);
	}
}
