package craterzone.com.gateway.filter;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;


public class RoutingFilter extends ZuulFilter {

    private static final String REQUEST_PATH = "/api";
    private static final String TARGET_SERVICE = "PROJECT_ONE";
    private static final String HTTP_METHOD = "POST";

    private DiscoveryClient discoveryClient;

    

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        return HTTP_METHOD.equalsIgnoreCase(method) && requestURI.startsWith(REQUEST_PATH);
    }

    @Override
    public Object run() {

        RequestContext context = RequestContext.getCurrentContext();
        List<ServiceInstance> instances = discoveryClient.getInstances(TARGET_SERVICE);
        try {
            if (instances != null && instances.size() > 0) {
                context.setRouteHost(instances.get(0).getUri().toURL());
            } else {
                throw new IllegalStateException("Target service instance not found!");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Couldn't get service URL!", e);
        }
        return null;
}
}
