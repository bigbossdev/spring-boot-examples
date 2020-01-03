package io.bigboss.config;

import org.eclipse.jetty.http.CookieCompliance;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.ServerConnector;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<Filter> getFilterRegistrationBean() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.addUrlPatterns("/*");

        registrationBean.setFilter((request, response, chain) -> {
            HttpServletRequest req = (HttpServletRequest) request;
            System.out.println("[Cookie] " + req.getHeader("Cookie"));
            Cookie[] cookies = req.getCookies();

            for (Cookie c : cookies) {
                System.out.println(">> " + c.getName() + " => " + c.getValue());
            }

            chain.doFilter(request, response);
        });

        return registrationBean;
    }

    @Bean
    public WebServerFactoryCustomizer<JettyServletWebServerFactory> cookieProcessorCustomizer() {
        return factory -> factory.addServerCustomizers(server -> {
            for (Connector connector : server.getConnectors()) {
                if (connector instanceof ServerConnector) {
                    HttpConnectionFactory connectionFactory = ((ServerConnector) connector)
                            .getConnectionFactory(HttpConnectionFactory.class);
                    connectionFactory.getHttpConfiguration().setRequestCookieCompliance(CookieCompliance.RFC2965);
                }
            }
        });
    }

}
