package craterzone.com.gateway;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import craterzone.com.gateway.filter.PreFilter;

@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class ZuulApiGatewayApplication {

	Logger logger = LogManager.getLogger(ZuulApiGatewayApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ZuulApiGatewayApplication.class, args);

	}

	@Bean
	    public PreFilter preFilter() {
	        return new PreFilter();
	    }
}
/*
 * @Bean public PostFilter postFilter() { return new PostFilter(); }
 * 
 * @Bean public ErrorFilter errorFilter() { return new ErrorFilter(); }
 * 
 * @Bean public RouteFilter routeFilter() { return new RouteFilter(); }
 * 
 * }
 */
