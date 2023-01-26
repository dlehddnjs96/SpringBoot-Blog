package springboot.study.springbootblog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import springboot.study.springbootblog.model.KakaoProfile;
import springboot.study.springbootblog.model.OAuthToken;
import springboot.study.springbootblog.model.User;
import springboot.study.springbootblog.service.UserService;

import java.util.UUID;

@Controller
public class UserController {
    // 인증이 안된 사용자들이 출입 할 수 있는 경로룰 지정 :  /auth/*, /, static 이하에 있는 경로
    // 스프링 시큐리티에서는 /logout 기능을 자동으로 생성해준다. (securityHeader 에서 로그아웃에 /logout만 연결)
    // 로그아웃하여 인증이 없는 사용자는 시큐리티 설정에서 설정한 로그인 폼으로 이동하도록 설정

    // OAth 인증 (카카오로그인)에서 사용 (cos는 yml 파일에 선언)
    @Value("${cos.key}")
    private String cosKey;

    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/auth/joinForm")
    public String joinForm(){
        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm(){
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm(){
        return "user//updateForm";
    }
    

    @GetMapping("/auth/kakao/callback")
    // Kakao 로그인
    // Data를 리턴해주는 컨트롤러 함수
    public String kakaoLogin(String code){
        // 사용자 동의 잘못 설정 : https://accounts.kakao.com/weblogin/account/partner3 사이트에서 계정이용 -> 외부 서비스에서 연결끊고 다시 설정
        // POST 방식으로 key=value 토큰 데이터를 요청 (@ResponseBody String kakaoLogin ~~, 페이지 리다이렉션을 위해 제거하였고 데이터를 보고 싶다면 다시 붙이면 된다.)
        RestTemplate rt = new RestTemplate();

        // Header 데이터 받기 (오브젝트 생성)
        HttpHeaders headers = new HttpHeaders();
        // 카카오 개발 사이트에서 문서 -> 개발 -> REST API -> 토큰에서 확인
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // Body 데이터 받기 (오브젝트 생성)
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        // 카카오 개발 사이트에서 문서 -> 개발 -> REST API -> 토큰 -> Parameter 에서 확인
        params.add("grant_type","authorization_code");
        params.add("client_id","8624aab618e29f5d6c8e225b3d09766e");
        params.add("redirect_uri","http://localhost:8000/auth/kakao/callback");
        params.add("code", code);
        
        // Header, Body 하나의 오브젝트로 담기
        HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        // Http 요청하기
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class);

        // 응답온 JSON 데이터 JAVA 오브젝트로 담기 (Model - OAuthToken 생성)
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;
        try {
            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("oAuthToken : " + oAuthToken.getAccess_token());


        // 사용자 정보 가져오기
        // POST 방식으로 key=value 토큰 데이터를 요청
        RestTemplate rt2 = new RestTemplate();

        // Header 데이터 받기 (오브젝트 생성)
        HttpHeaders headers2 = new HttpHeaders();
        // 카카오 개발 사이트에서 문서 -> 개발 -> REST API -> 사용자 정보 가져오기 확인
        headers2.add("Authorization", "Bearer "+ oAuthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // Header, Body 하나의 오브젝트로 담기
        HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest = new HttpEntity<>(headers2);

        // Http 요청하기
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class);
        // https://www.jsonschema2pojo.org/ 사이트를 이용하여 kakao profile에 대한 정보를 저장할 model 클래스 쉽게 생성하여 자세한 부분은 변경
        // https://jsonlint.com/ 사이트를 통해 JSON 데이터를 정렬하여 필드명 비교
        // Shift + End 로 쉽게 한줄 복사
        System.out.println("Profile : " + response2.getBody());

        // 응답온 JSON 데이터 JAVA 오브젝트로 담기 (Model - KakaoProfile 생성)
        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        // User 오브젝트는 username, email, password 3개의 데이터가 필요 (password 데이터는 내 쪽에서는 알고있을 필요없어서 임의로 저장)
        System.out.println("kakaoProfile : " + kakaoProfile.getId());
        System.out.println("kakaoProfile : " + kakaoProfile.getKakao_account().getEmail());
        System.out.println("블로그서버 저장 유저네임(카카오로그인) : "+ "k_" + kakaoProfile.getKakao_account().getProfile().getNickname());
        System.out.println("블로그서버 저장 이메일(카카오로그인) : "+ kakaoProfile.getKakao_account().getEmail());
        System.out.println("블로그서버 저장 비밀번호(카카오로그인) : "+ cosKey);

        User kakaoUser = User.builder()
                .username("k_" + kakaoProfile.getKakao_account().getProfile().getNickname())
                .password(cosKey)
                .email(kakaoProfile.getKakao_account().getEmail())
                .oauth("kakao")
                .build();

        // 가입자 비가입자 체크해서 처리 (비가입자면 빈 객체가 반환)
        User originUser = userService.find(kakaoUser.getUsername());
        if (originUser.getUsername() == null) {
            System.out.println("카카오 계정으로 회원가입 완료되었습니다.");
            userService.join(kakaoUser);
        }
        // 가입자는 바로 로그인 처리 (비가입자는 회원 가입후 로그인처리)
        System.out.println("카카오 로그인이 완료되었습니다.");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(),cosKey));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";
//        return "Kakao Authentication Completed </br>"
//                + "Code : " + code + "</br>"
//                + "Token : " + response + "</br>"
//                + "사용자 정보 : " + response2.getBody() + "</br>";
    }
}
