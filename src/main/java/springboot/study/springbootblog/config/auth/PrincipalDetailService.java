package springboot.study.springbootblog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springboot.study.springbootblog.model.User;
import springboot.study.springbootblog.repository.UserRepository;

@Service
public class PrincipalDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // 스프링 시큐리티가 로그인 요청을 가로챌 때 password 부분은 자동으로 처리가 되고 username 이 DB와 일치하는지 확인
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User principal = userRepository.findByUsername(username).orElseThrow(() ->{
            return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다.");
        });
        // 시큐리티 세션에 유저 정보가 저장
        return new PrincipalDetail(principal);
    }
}
