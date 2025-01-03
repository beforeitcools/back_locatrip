package com.ohgiraffers.jenkins_test_app.config;

import com.ohgiraffers.jenkins_test_app.auth.filter.HeaderFilter;
import com.ohgiraffers.jenkins_test_app.auth.interceptor.JwtTokenInterceptor;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc       // Spring MVC를 활성화하여 웹 애플리케이션에서 MVC 기능을 사용할 수 있게 해줌
public class WebConfig implements WebMvcConfigurer {

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


    // ResourceHandlerRegistry를 사용해 정적 자원에 대한 핸들러를 추가
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String commonPath = "file:///C:/locat/";

        registry.addResourceHandler("/images/user/profilePic/**")
                .addResourceLocations(commonPath + "profile_pic/");
    }


    // 외부 서버의 요청을 허용 한다.
    @Bean
    public FilterRegistrationBean<HeaderFilter> getFilterRegistrationBean(){
        // HeaderFilter 필터를 등록하고, 필터가 모든 요청에 대해 동작하도록 설정
        FilterRegistrationBean<HeaderFilter> registrationBean = new FilterRegistrationBean<HeaderFilter>(creatHeaderFilter());
        registrationBean.setOrder(Integer.MIN_VALUE);  // 필터의 우선순위 설정 (가장 먼저 실행되도록 설정)
        registrationBean.addUrlPatterns("/*"); // 모든 URL 패턴에 대해 필터를 적용
        return registrationBean;
    }

    // HeaderFilter 빈 생성
    @Bean
    public HeaderFilter creatHeaderFilter(){
        return new HeaderFilter();  // HeaderFilter 객체 반환
    }

    // JwtTokenInterceptor 빈 생성
    @Bean
    public JwtTokenInterceptor jwtTokenInterceptor(){
        return new JwtTokenInterceptor(); // JwtTokenInterceptor 객체 반환
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // JwtTokenInterceptor를 모든 경로에 대해 적용
        registry.addInterceptor(jwtTokenInterceptor())
                .addPathPatterns("/**")  // 모든 요청 경로에 대해 인터셉터 적용
                // 로그인, 회원가입과 같은 예외 경로
                .excludePathPatterns("/auth/login", "/auth/signup", "/auth/checkUserId", "/auth/checkNickname", "/auth/logout", "/auth/refreshAccessToken");
    }



}
