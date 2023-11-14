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

                        // MEMBERSHIPS CONTROLLER
                        .requestMatchers(HttpMethod.GET,"/api/subscription").permitAll()
                        .requestMatchers("/api/subscription").hasRole("MEMBER")
                        .requestMatchers("/api/trainers/subscription").hasRole("TRAINER")
                        .requestMatchers("/api/admin/subscription").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST,"/api/members").permitAll()
                        .requestMatchers("/api/members").hasRole("MEMBER")
                        .requestMatchers("/api/members/goals").hasRole("MEMBER")
                        .requestMatchers("/api/admin/members").hasRole("ADMIN")
                        .requestMatchers("/api/admin/members/**").hasRole("ADMIN")

                        .requestMatchers("/api/personalTrainers").hasRole("MEMBER")
                        .requestMatchers("/api/trainers").hasRole("TRAINER")
                        .requestMatchers("/api/trainers/goals/**").hasAnyRole("TRAINER", "ADMIN")
                        .requestMatchers("/api/trainers/clients").hasRole("TRAINER")
                        .requestMatchers("/api/admin/trainers").hasRole("ADMIN")
                        .requestMatchers("/api/admin/trainers/**").hasRole("ADMIN")


                        // AUTHENTICATION CONTROLLER
                        .requestMatchers("/api/principal").authenticated()
                        .requestMatchers("api/login").permitAll()
                        .anyRequest().denyAll())

                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
