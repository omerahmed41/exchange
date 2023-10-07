package com.exchange.application.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {


    /**
     * Configures the default security filter chain.
     *
     * Subclasses may override this method to customize the security filter chain. However,
     * subclasses should be careful not to remove any of the default security filters, as this
     * could leave the application vulnerable to attacks.
     *
     * @return the default security filter chain
     */
    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);

        // Check if 'user' exists before creating
        if (!userDetailsManager.userExists("user")) {
            UserDetails user = User.builder()
                    .username("user")
                    .password("{noop}test123")
                    .roles("USER")
                    .build();
            userDetailsManager.createUser(user);
        }

        // Check if 'admin' exists before creating
        if (!userDetailsManager.userExists("admin")) {
            UserDetails admin = User.builder()
                    .username("admin")
                    .password("{bcrypt}$2a$12$rAODaMRLfi8vm3wHApyYeeMF95HBA2UFbLGGgp3SzEcrHfm/BaI16")
                    .roles("USER", "ADMIN")
                    .build();
            userDetailsManager.createUser(admin);
        }

        return userDetailsManager;
    }

    /**
     * Configures the default security filter chain.
     *
     * Subclasses may override this method to customize the security filter chain. However,
     * subclasses should be careful not to remove any of the default security filters, as this
     * could leave the application vulnerable to attacks.
     *
     * @return the default security filter chain
     */
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

}


