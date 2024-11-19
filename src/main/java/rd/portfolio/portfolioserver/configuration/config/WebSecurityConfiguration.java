package rd.portfolio.portfolioserver.configuration.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import rd.portfolio.portfolioserver.configuration.jwt.JwtAuthenticationFilter;
import rd.portfolio.portfolioserver.configuration.jwt.JwtUtil;
import rd.portfolio.portfolioserver.service.UserDetailService;

;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    private final UserDetailService userSecurityService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(req -> req.requestMatchers("/auth/*")
                                                     .permitAll()
                                                     .anyRequest()
                                                     .authenticated())
                    //                    .formLogin(form -> form.loginPage("/login")
                    //                                           .defaultSuccessUrl("/users")
                    //                                           .loginProcessingUrl("/auth/login")
                    //                                           .permitAll()
                    //                              )
                    .sessionManagement(ssession -> ssession.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    //                    .authenticationProvider(authenticationProvider)
                    .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userSecurityService), UsernamePasswordAuthenticationFilter.class);
        //                .exceptionHandling(ex->ex.authenticationEntryPoint(unauthorizedHandler))

        return httpSecurity.build();
    }

}
