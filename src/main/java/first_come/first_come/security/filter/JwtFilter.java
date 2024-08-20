package first_come.first_come.security.filter;

import first_come.first_come.domain.user.User;
import first_come.first_come.security.service.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //request에서 먼저 Authoriation 헤더를 찾는다.
        String authorization = request.getHeader("Authorization");

        //Authoriation 헤더에 "Bearer "가 있는지 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            System.out.println("jwt 필터 : token null");

            // doFilter를 통해서 필터 체인을 넘어간다. 다음 필터로 넘기는 것.
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        // 검증에서 문제가 없으면 "Bearer "를 분리해준다.
        String token = authorization.split(" ")[1];

        // 토큰의 유효기간을 확인한다. JwtUtil 객체에서 구현해놓은 메소드를 통해서
        if (jwtUtil.isExpired(token)) {

            System.out.println("token expired");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        // 최종적으로 유효한 토큰이 존재하면 일시적인 세션을 만들어서 security 컨텍스트홀더라는 시큐리티 세션에다가 유저를 저장해주어
        // 유저가 특정 경로를 요청할 때 진행할 수 있게 한다.
        String username = jwtUtil.getUsername(token);

        //userEntity를 생성하여 값 set
        User userEntity = new User();
        userEntity.setEmail(username);
        userEntity.setPassword("temppassword");  // 비밀번호를 요청해야할 때마다 db에서 비밀번호를 확인하는건 비효율적이기 때문에 컨텍스트 홀더에 정확한 비밀번호를 넣지 않겠다. 일시적으로 비밀번호를 강제로 만들어 설정해준다.

        //UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        //검증이 완료됏으니 다음 필터로 넘긴다.
        filterChain.doFilter(request, response);

    }
}
