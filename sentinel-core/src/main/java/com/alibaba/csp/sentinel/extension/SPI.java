package com.alibaba.csp.sentinel.extension;

import java.lang.annotation.*;

/**
 * Created by admin on 2019/7/9.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SPI
{
	/**
	 * default extension name
	 */
	String value();
}
