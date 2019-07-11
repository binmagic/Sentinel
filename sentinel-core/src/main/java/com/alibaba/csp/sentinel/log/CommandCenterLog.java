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
package com.alibaba.csp.sentinel.log;

import com.alibaba.csp.sentinel.log.sentinel.SentinelLoggerAdapter;

/**
 * Logger for command center.
 */
public class CommandCenterLog extends LogBase
{

	public static final com.alibaba.csp.sentinel.log.Logger log = LoggerFactory.getLogger(SentinelLoggerAdapter.CSP_COMMAND_CENTER_LOG);

	public static void info(String detail, Object... params)
	{
		log.info(detail, params);
	}

	public static void info(String detail, Throwable e)
	{
		log.info(detail, e);
	}

	public static void warn(String detail, Object... params)
	{
		log.warn(detail, params);
	}

	public static void warn(String detail, Throwable e)
	{
		log.warn(detail, e);
	}
}
