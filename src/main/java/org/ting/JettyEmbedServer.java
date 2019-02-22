package org.ting;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;
import org.ting.spring.WebRootConfiguration;

public class JettyEmbedServer {

	private final static int DEFAULT_PORT = 9999;

	private final static String DEFAULT_CONTEXT_PATH = "/";

	private final static String MAPPING_URL = "/*";

	public static void main(String[] args) throws Exception {
		Server server = new Server(DEFAULT_PORT);
		JettyEmbedServer helloServer = new JettyEmbedServer();
		server.setHandler(helloServer.servletContextHandler());
		server.start();
		server.join();

	}

	private ServletContextHandler servletContextHandler() {
		WebApplicationContext context = webApplicationContext();
		ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
		servletContextHandler.setContextPath(DEFAULT_CONTEXT_PATH);
		ServletHolder servletHolder = new ServletHolder(new DispatcherServlet(context));
		servletHolder.setAsyncSupported(true);
		servletContextHandler.addServlet(servletHolder, MAPPING_URL);
		//手动注册拦截器，让Spring Security 生效
		FilterHolder filterHolder = new FilterHolder(new DelegatingFilterProxy("springSecurityFilterChain"));
		servletContextHandler.addFilter(filterHolder, MAPPING_URL, null);
		servletContextHandler.addEventListener(new ContextLoaderListener(context));
		servletContextHandler.addEventListener(new HttpSessionEventPublisher()); //使用security session 监听器 限制只允许一个用户登录
		return servletContextHandler;
	}

	private WebApplicationContext webApplicationContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebRootConfiguration.class);
        return context;
    }

}
