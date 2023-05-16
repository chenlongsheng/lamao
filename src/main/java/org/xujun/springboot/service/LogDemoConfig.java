/**
 * 
 */
package org.xujun.springboot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author admin
 *
 */
@Configuration
public class LogDemoConfig {
	 Logger logger = LoggerFactory.getLogger(getClass());

	    @Bean
	    public String logMethod(){
	        // 从trace到error日志级别由低到高
	        // 可以调整输出的日志级别，日志就只会在这个级别后的高级别生效
//	        logger.trace("LogDemoConfig trace日志...");
//	        logger.debug("LogDemoConfig debug日志...");
//	        logger.info("LogDemoConfig info日志...");
//	        logger.warn("LogDemoConfig warn日志...");
//	        logger.error("LogDemoConfig error日志...");
	        return "hello log";
	    }
 

}
