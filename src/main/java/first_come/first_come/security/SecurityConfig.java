package first_come.first_come.security;

import first_come.first_come.security.filter.JwtFilter;
import first_come.first_come.security.filter.JwtUtil;
import first_come.first_come.security.filter.LoginFilter;
import first_come.first_come.security.service.CustomUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain FilterChain(HttpSecurity http, CustomUserDetailsServiceImpl customUserDetailsServiceImpl) throws Exception {

        // Create LoginFilter and JwtFilter instances
        AuthenticationManager authManager = authenticationManager(authenticationConfiguration);
        LoginFilter loginFilter = new LoginFilter(authManager, jwtUtil);
        JwtFilter jwtFilter = new JwtFilter(jwtUtil, customUserDetailsServiceImpl);

        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/api/signup", "/api/verify-email").permitAll()
                        .anyRequest().authenticated())

                .addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class) // LoginFilter를 UsernamePasswordAuthenticationFilter 전에 추가
                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class) // JwtFilter를 UsernamePasswordAuthenticationFilter 후에 추가

                // 세션 설정
                .sessionManagement((session) ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}


