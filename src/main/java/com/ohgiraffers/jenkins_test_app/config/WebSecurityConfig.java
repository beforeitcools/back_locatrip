package com.ohgiraffers.jenkins_test_app.config;


import com.ohgiraffers.jenkins_test_app.auth.filter.CustomAuthenticationFilter;
import com.ohgiraffers.jenkins_test_app.auth.filter.JwtAuthorizationFilter;
import com.ohgiraffers.jenkins_test_app.auth.handler.CustomAuthFailureHandler;
import com.ohgiraffers.jenkins_test_app.auth.handler.CustomAuthSuccessHandler;
import com.ohgiraffers.jenkins_test_app.auth.handler.CustomAuthenticationProvider;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration  // 설정된 리소스를 읽어 환경 구성 - 시작 시
@EnableGlobalMethodSecurity(prePostEnabled = true)  // 컨트롤러에서 특정 권한이 있는 사용자만 가능하도록 하는 기능을  켜줌
@EnableWebSecurity  //  Spring Security의 웹 보안을 활성화
public class WebSecurityConfig {

    // 각각을 @Bean 메서드를 통해 분리하여 정의하는 이유는 유지보수성, 확장성, 의존성 주입 관리, 테스트 용이성, 책임 분리 등의 여러 가지 장점을 얻기 위함
    // 이를 통해 각 컴포넌트가 독립적으로 동작하면서도, 필요한 경우 의존성을 쉽게 주입받고, 나중에 확장하거나 수정하는 데 유리한 구조를 만들 수 있음

    /**
     * 1. 정적 자원에 대한 인증된 사용자의 접근을 설정하는 메소드
     *
     * @return WebSecurityCustomizer
     * */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){

        // 요청 리소스가 static resources를 등록하지 않겠다.
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    /**
     * security filter chain 설정
     *
     * @return SecurityFilterChain
     * */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Cross-Site Request Forgery (CSRF) 보호를 비활성화합니다.
        // CSRF는 주로 세션 기반 인증에서 사용되며, JWT 기반 인증에서는 클라이언트가 상태를 관리하기 때문에 큰 의미가 없습니다.
        // JWT는 요청마다 인증 토큰을 포함하므로 세션과 연관되지 않기 때문입니다. CSRF는 사용자가 로그인한 상태에서 악의적인 웹사이트가 사용자의 인증된 세션을 이용해 원하지 않는 요청을 보내는 공격입니다
        http.csrf(csrf -> csrf.disable())
                // JWT 인증 필터를 BasicAuthenticationFilter 앞에 추가합니다.
                // JWT를 사용하는 경우 기본 HTTP 기본 인증 대신 JWT 인증 필터를 사용하도록 설정합니다.
                .addFilterBefore(jwtAuthorizationFilter(), BasicAuthenticationFilter.class)
                // 세션 관리 설정을 Stateless로 설정합니다.
                // 이 설정은 서버가 세션을 유지하지 않도록 하여, JWT를 사용하여 클라이언트 측에서 인증 정보를 유지하게 만듭니다.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 시큐리티를 통해서 세션을 만들지 않겠다는 선언 .
                // 기본적으로 제공되는 로그인 폼을 비활성화합니다.
                .formLogin(form -> form.disable())  // 제공되는 폼을 사용하지 않겠다는 의미
                // 커스텀 인증 필터를 UsernamePasswordAuthenticationFilter 앞에 추가합니다.
                // 기본적으로 제공되는 UsernamePasswordAuthenticationFilter 대신, 사용자 정의 필터를 사용하도록 설정합니다.
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) // 이것도 뒤에를 대신해서 앞에껄 만든다는 의미
                // HTTP Basic 인증은 클라이언트가 사용자 이름과 비밀번호를 기본 헤더로 보내는 방식인데, 우리는 JWT 인증을 사용하므로 이를 비활성화합니다.
                .httpBasic(basic -> basic.disable());

        return http.build();  // http 만 보내면 에러남. 만든걸 필터체인에 넣어줌
    }

    /**
     * 3. Authentication의 인증 메소드를 제공하는 매니저로 Provider의 인터페이스를 의미한다.
     * @return AuthenticationManager
     * */
    @Bean
    public AuthenticationManager authenticationManager(){
        //Spring Security에서 사용자 인증을 관리하는 핵심 객체
        return new ProviderManager(customAuthenticationProvider());
    }   //ProviderManager는 AuthenticationManager를 구현한 클래스이며, 여러 AuthenticationProvider를 통해 인증을 처리합니다.

    /**
     * 4. 사용자의 아이디와 패스워드를 DB와 검증하는 handler이다.
     *
     * @return CustomAuthenticationProvider
     * */
    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider(){

        return new CustomAuthenticationProvider();
    }

    /**
     *  5. 비밀번호를 암호화 하는 인코더
     *
     * @return BCryptPasswordEncoder
     * */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     *  6. 사용자의 인증 요청을 가로채서 로그인 로직을 수행하는 필터
     *
     * @return CustomAuthenticationFilter
     * */
    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter(){
        CustomAuthenticationFilter authenticationFilter = new CustomAuthenticationFilter(authenticationManager());
        // 3번에서 만든 매니저
        authenticationFilter.setFilterProcessesUrl("/login");  // 어떤 요청 리소스를 가로챌지
        authenticationFilter.setAuthenticationSuccessHandler(customAuthSuccessHandler()); // 요청 성공 동작
        authenticationFilter.setAuthenticationFailureHandler(customAuthFailureHandler()); // 요청 실패 동작
        authenticationFilter.afterPropertiesSet(); // 선택적으로 사용할 수 있는 메서드이며, 필터 초기화 후 추가 설정이 필요할 때만 호출됩니다.

        return authenticationFilter; // 필터 객체를 반환해야 Spring Security 필터 체인에 필터를 등록할 수 있기 때문입니다.
    }

    /**
     * 7. spring security 기반의 사용자의 정보가 맞을 경우 결과를 수행하는 handler
     * @return customAuthLoginSuccessHandler
     * */
    @Bean
    public CustomAuthSuccessHandler customAuthSuccessHandler(){
        return new CustomAuthSuccessHandler();
    }

    /**
     * 8. spring security 의 사용자 정보가 맞지 않은 겨우 수행되는 메소드
     * @return CustomAuthFailUreHandler
     * */
    @Bean
    public CustomAuthFailureHandler customAuthFailureHandler(){
        return new CustomAuthFailureHandler();
    }

    /**
     * 9. 사용자 요청 시 수행되는 메서드
     * @return JwtAuthorizationFilter
     * */
    public JwtAuthorizationFilter jwtAuthorizationFilter(){

        return new JwtAuthorizationFilter(authenticationManager());  // 만들어둔 매니저를 매개변수로 등록
    }

}
