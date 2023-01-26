package springboot.study.springbootblog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springboot.study.springbootblog.config.auth.PrincipalDetail;
import springboot.study.springbootblog.config.auth.PrincipalDetailService;
import springboot.study.springbootblog.dto.ResponseDto;
import springboot.study.springbootblog.model.RoleType;
import springboot.study.springbootblog.model.User;
import springboot.study.springbootblog.service.UserService;

import javax.servlet.http.HttpSession;

@RestController
public class UserApiController {
    @Autowired
    private UserService userService;

    @Autowired
    private PrincipalDetailService principalDetailService;

    @Autowired
    private AuthenticationManager authenticationManager;


    // user.js 에서 들어오는 요청에 대합 응답 로직
    @PostMapping("/auth/joinProc")
    // 회원가입
    public ResponseDto<Integer> save(@RequestBody User user){
        System.out.println("User 회원가입이 완료되어 DB에 저장합니다.");
        userService.join(user);
        // 자바오브잭트를 Json으로 변환해서 리턴 (Jackson)
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    // 스프링 시큐리티를 사용하기 전에 사용하던 방법
    // 스프링 시큐리티를 사용하면 시큐리티 설정(SecurityConfig)에서 로그인 기능을 구현
    // 스프링 시큐리티에서는 /logout 기능을 자동으로 생성해준다. (securityHeader에서 로그아웃에 /logout만 연결)
    @PostMapping("/api/user/login")
    public ResponseDto<Integer> login(@RequestBody User user, HttpSession session){
        System.out.println(user.getUsername() + " 님이 로그인 했습니다.");
        // userService에서 DB 데이터와 요청으러 들어오는 데이터를 비교하여 같으면 데이터를 리턴 다르다면 null값이 리턴된다.
        User principal = userService.login(user); // principal : 접근주체

        if (principal != null){
            // 세션 생성
            session.setAttribute("principal",principal);
            return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
        }
        return new ResponseDto<Integer>(HttpStatus.INTERNAL_SERVER_ERROR.value(),-1);
    }
    
    @PutMapping("/user")
    // 회원정보 수정
    public ResponseDto<Integer> update(@RequestBody User user) {
        System.out.println(user.getUsername() + " 님의 회원정보가 수정되었습니다.");
        userService.update(user);
        // 여기서는 트랜잭션이 종료되기 때문에 DB에 값은 변경이 되었지만 세션값은 변경되지 않은 상태이기때문에 바로 적용 되지 않는다.
        // 따라서 직접 세션값을 변경해줘야한다.
        // AuthenticationManger로 만들면 UserDetailService가 username으로 db에 질의해서 있는지 확인후 있으면 그걸 토대로
        // 비밀번호 암호화 후 그 암호화된 비밀번호까지 db 질의를 통해 확인 한 뒤 시큐리티 컨텍스트에 넣는것이다.
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // AuthenticationManger 사용하지 않고 세션값 변경
        // UserDetails userDetails = principalDetailService.loadUserByUsername(user.getUsername());
        // Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        // SecurityContext securityContext = SecurityContextHolder.getContext();
        // securityContext.setAuthentication(authentication);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }




}
