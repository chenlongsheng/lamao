/**
 * 
 */
package  org.xujun.springboot.websoket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
 

/**
 * @author admin
 *
 */
@Configuration
//@EnableWebSocket
public class WebsocketConfig {

	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
	
	

}






