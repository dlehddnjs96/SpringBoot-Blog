package springboot.study.springbootblog.config.auth;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import springboot.study.springbootblog.model.User;

import java.util.ArrayList;
import java.util.Collection;

// BoardService에서 User 객체 사용을 위해 BoardController에서 PrincipalDetail의 User 객체를 Getter로 사용
@Getter
public class PrincipalDetail implements UserDetails {

    // 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
    // 스프링 시큐리티의 고유한 세션 저장소에 저장

    @Autowired
    private User user;

    public PrincipalDetail(User user) {
        this.user = user;
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정이 만료되지 않았는지 리턴 (true : 만료안됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겼는지 않았는지 리턴 (true : 잠기지안됨)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되지 않았는지 리턴 (true : 만료안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성화 되었는지 안되었는지 리턴 (true : 활상화)
    @Override
    public boolean isEnabled() {
        return true;
    }
    
    // 계정의 권한을 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collections = new ArrayList<>();

        // "ROLE_"는 스프링 시큐리티의 규칙으로 반드시 포함시켜애 한다.
        collections.add(()-> {return "ROLE_"+user.getRole();});
        return collections;
    }
}
