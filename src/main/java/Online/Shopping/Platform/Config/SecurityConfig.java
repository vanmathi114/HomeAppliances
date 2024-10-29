//package Online.Shopping.Platform.Config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        var userDetailsManager = new InMemoryUserDetailsManager();
//
//        var admin = User.withUsername("admin")
//                .password("{noop}admin123")
//                .roles("ADMIN")
//                .build();
//
//        var user = User.withUsername("user")
//                .password("{noop}user123")
//                .roles("USER")
//                .build();
//
//        userDetailsManager.createUser(admin);
//        userDetailsManager.createUser(user);
//
//        return userDetailsManager;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/admin/**").hasRole("ADMIN")
//                .anyRequest().authenticated()
//            )
//            .httpBasic();
//
//        return http.build();
//    }
//}
