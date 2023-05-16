/**
 * 
 */
package org.xujun.springboot.task;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 

/**
 * @author admin
 *
 */

@Controller
public class TaskControl  {

	@Autowired
	private AsyncServiceImpl asyncService;

	/**
	 * 跳转到注册页面
	 * 
	 * @throws IOException
	 */
//    @RequestMapping("/register2")
//    public String goRegister2(){
//
//        return "register2";
//    }

	@RequestMapping("/task")
	public void task() throws IOException {

		System.out.println("haha");
 

		// 调用service层的任务
//		asyncService.executeAsync();

	}

}
