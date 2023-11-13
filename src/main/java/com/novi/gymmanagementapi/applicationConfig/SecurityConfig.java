package com.novi.gymmanagementapi.applicationConfig;

import com.novi.gymmanagementapi.filters.JwtRequestFilter;
import com.novi.gymmanagementapi.services.MyCustomMemberDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public final MyCustomMemberDetailsService myCustomMemberDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(MyCustomMemberDetailsService myCustomMemberDetailsService,
                          JwtRequestFilter jwtRequestFilter) {
        this.myCustomMemberDetailsService = myCustomMemberDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        var auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder);
        auth.setUserDetailsService(myCustomMemberDetailsService);
        return new ProviderManager(auth);
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests((auth) -> auth

                        // memberships
                        .requestMatchers(HttpMethod.GET,"/api/memberships").permitAll()
                        .requestMatchers("/api/admin/memberships").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/memberships/subscription/**").hasRole("MEMBER")
                        .requestMatchers(HttpMethod.DELETE, "/api/memberships/subscription/**").hasRole("MEMBER")

                        // trainers
                        .requestMatchers("api/personalTrainer").hasAnyRole("ADMIN", "TRAINER", "MEMBER")
                        .requestMatchers("api/personalTrainer").hasAnyRole("ADMIN", "TRAINER", "MEMBER")
                        .requestMatchers(HttpMethod.GET, "/api/trainers").hasAnyRole("ADMIN", "TRAINER")
                        .requestMatchers(HttpMethod.POST, "/api/trainers").hasRole("ADMIN")

                        // members
                        .requestMatchers(HttpMethod.POST,"/api/members").permitAll()
                        .requestMatchers("/api/members").hasAnyRole("ADMIN", "TRAINER", "MEMBER")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/members/**").hasRole("ADMIN")

                        // login
                        .requestMatchers("/api/principal").authenticated()
                        .requestMatchers("api/login").permitAll()
                        .anyRequest().denyAll())

                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
