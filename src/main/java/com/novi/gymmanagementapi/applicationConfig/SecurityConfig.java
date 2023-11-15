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
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
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

                        // OPEN ENDPOINTS
                        .requestMatchers("api/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/account").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/memberships").permitAll()

                        // MEMBER ENDPOINTS
                        .requestMatchers("/api/members/account").hasRole("MEMBER")
                        .requestMatchers("/api/members/subscription").hasRole("MEMBER")
                        .requestMatchers("/api/members/personal-trainers").hasRole("MEMBER")
                        .requestMatchers("/api/members/goals/**").hasRole("MEMBER")
                        .requestMatchers("/api/members/goals/evaluations/**").hasRole("MEMBER")
                        .requestMatchers("/api/members/goals/meals/**").hasRole("MEMBER")

                        // TRAINER ENDPOINTS
                        .requestMatchers("/api/trainers/account").hasRole("TRAINER")
                        .requestMatchers("/api/trainers/subscription").hasRole("TRAINER")
                        .requestMatchers("/api/trainers/clients").hasRole("TRAINER")
                        .requestMatchers("/api/trainers/goals").hasAnyRole("TRAINER")
                        .requestMatchers("/api/trainers/goals/**").hasAnyRole("TRAINER")
                        .requestMatchers("/api/trainers/goals/evaluations/**").hasAnyRole("TRAINER")
                        .requestMatchers("/api/trainers/goals/meals/**").hasAnyRole("TRAINER")

                        // ADMIN ENDPOINTS
                        .requestMatchers("/api/admin/subscription").hasRole("ADMIN")
                        .requestMatchers("/api/admin/manage-members/**").hasRole("ADMIN")
                        .requestMatchers("/api/admin/manage-trainers/**").hasRole("ADMIN")

                        .anyRequest().denyAll())

                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
