package polish_community_group_11.polish_community.security;

import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;
import polish_community_group_11.polish_community.register.models.User;
import polish_community_group_11.polish_community.register.services.UserService;
import polish_community_group_11.polish_community.register.models.Role;
import polish_community_group_11.polish_community.register.services.RoleService;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    private final UserService userService;
    private final RoleService roleService;

    private final String[] whiteListingPath = {
//            "/event",
//            "event/*"
//            "/api/feed/**" ,
    };

    public WebSecurityConfig(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    // based on https://spring.io/guides/gs/securing-web

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                // require authentication for events only
                //update in future when more protected resources are available
                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/api/translations/**","/api/feed/**").permitAll()
//                        .requestMatchers().permitAll()
                        .requestMatchers(whiteListingPath).authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .failureUrl("/login?error=true")
                        .failureHandler((request, response, exception) -> {
                            String errorMessage = "Invalid username or password";
                            request.getSession().setAttribute("error", errorMessage);
                            response.sendRedirect("/login?error=true");
                        })
                        .defaultSuccessUrl("/", true)
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }

    @Bean
    public UserDetailsService userDetailsService() {
    return new UserDetailsService() {
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            try {
                User user = userService.findByEmail(username);
                if (user == null) {
                    LOG.error("Couldn't find user with this name: {}", username);
                    throw new UsernameNotFoundException(username);
                }

                Role role = roleService.findRoleById(user.getRoleId());  // Assuming getRoleId() returns the roleId
                String roleName = "ROLE_" + role.getName().toUpperCase();  // Format the role to ROLE_NAME

                //prefix all passwords with {noop} to allow login to run without adding encryption
                return new org.springframework.security.core.userdetails.User(user.getEmail(), "{noop}"+user.getPassword(), true, true,
                        true, true, Set.of(new SimpleGrantedAuthority(roleName)));
            } catch (DataAccessException e) {
                LOG.error("Error when finding user by email: {}", username, e);
                throw new UsernameNotFoundException(username);
            }
        }
    };
    }



}
