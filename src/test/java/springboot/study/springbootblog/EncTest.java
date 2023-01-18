package springboot.study.springbootblog;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncTest {

    // Junit를 이용하여 SecurityConfig에 encoderPWD 테스트
    @Test
    public void hashEncode(){
        String passwd = new BCryptPasswordEncoder().encode("1234");
        System.out.println(passwd);
    }
}
