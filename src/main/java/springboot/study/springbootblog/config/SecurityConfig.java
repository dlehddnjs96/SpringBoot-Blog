package springboot.study.springbootblog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import springboot.study.springbootblog.config.auth.PrincipalDetailService;

@Configuration // Bean 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 등록 (IoC)
@EnableWebSecurity // Security 필터를 추가하여 활성화된 스프링 시큐리티의 설정을 현재 클래스에서 하겠다고 선언
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근하면 권한 및 인증을 미리 체크
public class SecurityConfig  {

    // WebSecurityConfigurerAdapter deprecated 되어 SecurityFilterChain 사용
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // xss (Cross Site Scripting) : 자바스크립트 공격으로 사용자가 웹에 스크립트를 작성하여 서버를 공격하는 방법
        // 네이버에서 제공하는 오픈소스인 lucy 를 통하여 예방가능

        // csrf (Cross Site Request Forgery) 공격 : 사이트 간 요청 위조로 주소 패턴을 이용하여 정보를 변경하는 방식으로 변경
        // CSRF 토큰을 사용하여 사용자를 검증하는 방법과 다양한 방법으로 예방이 가능하다.
        // 스프링 시큐리티는 자바스크립트 요청이 들어올 때 CSRF 토큰이 없으면 자동으로 막아준다.

            // csrf 토큰 비활성화 (테스트할 때 걸어두는것이 좋다.)
        http.csrf().disable()
            // Request가 들어오면
            .authorizeHttpRequests()
            // auth가 포함된 모른 경로를 permitAll(허락)한다.
            .antMatchers("/","/auth/**","/js/**","/css/**","/image/**")
            .permitAll()
            // 위 경로가 아닌 모든 경로는 authenticated (인증)되어야 한다.
            .anyRequest()
            .authenticated()
            // 웹 접근 시 인덱스 페이지는 인증이 필요하므로 인증이 없는 사용자는 설정한 로그인 폼으로 이동하도록 설정
            .and()
            .formLogin()
            .loginPage("/auth/loginForm")
            // 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인을 해준다.
            .loginProcessingUrl("/auth/loginProc")
            // 로그인이 성공하면 이동하는 주소
            .defaultSuccessUrl("/");

        return http.build();
    }

    // 시큐리티 함수를 이용하여 비밀번호를 해시값으로 암호화
    @Bean
    public BCryptPasswordEncoder encoderPWD(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    PrincipalDetailService principalDetailService;

    //
    // WebSecurityConfigurerAdapter를 implements 한다면 @Override를 븉여서 사용해야하지만 현재 버전에서는 붙이지 않는다.
    // 스프링 시큐리티가 대신 로그인 해줄 때 ID, PW를 DB와 비교하여 로그인해여 하는데
    // DB에 저장된 해시 방식이 무엇인지 알려주면 시큐리티에서 자동으로 DB와 비교
    // DB에 저장된 ID와 비교하기 위해 PrincipalDetailService 에서는 시큐리티 세션에 유저 정보가 저장
    // SecurityFilterChain를 사용하는 버전부터는 ID, PW 모두 시큐리티에서 @Bean이 자동으로 비교하여 로그인 시켜주기 때문에
    // configure, PrincipalDetailService 모두 필요없다.
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(principalDetailService).passwordEncoder(encoderPWD());
    }

}
