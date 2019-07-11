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
package com.alibaba.csp.sentinel.log.sentinel;

import com.alibaba.csp.sentinel.log.LogBase;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/***
 * The basic logger for vital events.
 *
 * @author youji.zj
 */
public class RecordLog extends LogBase implements com.alibaba.csp.sentinel.log.Logger
{
    private static final Logger heliumRecordLog = Logger.getLogger("cspSentinelRecordLog");
    private static final String FILE_NAME = "sentinel-record.log";
    private static Handler logHandler = null;

    static {
        logHandler = makeLogger(FILE_NAME, heliumRecordLog);
    }
    private static final RecordLog instance = new RecordLog();

    public static RecordLog getInstance(){
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
        log(heliumRecordLog, logHandler, Level.FINE, e.getMessage(), e);
    }

    @Override
    public void debug(Throwable e)
    {
        log(heliumRecordLog, logHandler, Level.FINE, e.getMessage(), e);
    }

    @Override
    public void error(Throwable e)
    {
        log(heliumRecordLog, logHandler, Level.SEVERE, e.getMessage(), e);
    }

    @Override
    public void info(Throwable e)
    {
        log(heliumRecordLog, logHandler, Level.INFO, e.getMessage(), e);
    }

    @Override
    public void warn(Throwable e)
    {
        log(heliumRecordLog, logHandler, Level.WARNING, e.getMessage(), e);
    }

    @Override
    public boolean isTraceEnabled() {
        return heliumRecordLog.isLoggable(Level.FINER);
    }

    @Override
    public boolean isDebugEnabled() {
        return heliumRecordLog.isLoggable(Level.FINE);
    }

    @Override
    public boolean isInfoEnabled() {
        return heliumRecordLog.isLoggable(Level.INFO);
    }

    @Override
    public boolean isWarnEnabled() {
        return heliumRecordLog.isLoggable(Level.WARNING);
    }

    @Override
    public boolean isErrorEnabled() {
        return heliumRecordLog.isLoggable(Level.SEVERE);
    }
}
