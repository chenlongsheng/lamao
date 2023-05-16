/**
 * 
 */
package org.xujun.springboot.service;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.xujun.springboot.dao.HistoryDao;

/**
 * @author admin
 *
 */

@Configuration
@ComponentScan("org.xujun.springboot.service")
public class ThreadStartService {
	
	
	 
	    @Bean(initMethod = "init", destroyMethod = "destroy")
	    ThreadInitService test1() {
	       return new ThreadInitService();
	    }
	
	
	
	

}
