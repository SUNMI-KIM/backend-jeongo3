package kr.kookmin.jeongo3.Security;


import kr.kookmin.jeongo3.Security.Jwt.JwtFilter;
import kr.kookmin.jeongo3.Security.Jwt.JwtProvider;
import kr.kookmin.jeongo3.Security.Jwt.JwtService;
import kr.kookmin.jeongo3.User.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtService jwtService;

    private static final String[] PERMIT_URL_ARRAY = {
            /* swagger v2 */
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            /* swagger v3 */
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/user").permitAll()
                .requestMatchers("/login", "/user/validation", "/refresh", "/users").permitAll()
                .requestMatchers("comment", "/user", "/user/report", "/post-like", "/DISC", "/DISC-headcount", "/post", "/posts", "/hot-post").hasAnyRole("UNIV", "HIGH", "ADMIN")
                .anyRequest().denyAll()

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                //.accessDeniedHandler(new CustomAccessDeniedHandler(objectMapper()))

                .and()
                .addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(
                        new LoginFilter(
                                jwtProvider, authenticationManager(authenticationConfiguration), jwtService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
