package com.ohgiraffers.jenkins_test_app.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

/*
    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addAdditionalTomcatConnectors(createHttpConnector()); // HTTP를 HTTPS로 리디렉션
        return factory;
    }

    private Connector createHttpConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(8081);  // HTTP 포트
        connector.setSecure(false);
        connector.setRedirectPort(8082);  // HTTPS 포트
        return connector;
    }
*/

    // local 환경(개발시에는) HTTP 요청을 HTTPS 로 변환해주어서는 안된다.
}
