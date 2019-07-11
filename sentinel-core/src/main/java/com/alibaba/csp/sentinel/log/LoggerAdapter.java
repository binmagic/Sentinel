package com.alibaba.csp.sentinel.log;

import com.alibaba.csp.sentinel.extension.SPI;

/**
 * Created by admin on 2019/7/9.
 */
@SPI("sentinel")
public interface LoggerAdapter
{
	Logger getLogger(String name);
}
