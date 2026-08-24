package org.ghotel.ghotel.security;

import org.ghotel.ghotel.entity.Employee;
import org.ghotel.ghotel.repository.EmployeeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserDetailsConfiguration {

    private final EmployeeRepository employeeRepository;

    public UserDetailsConfiguration(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
//        return new UserDetailsService() { //interface
//            @Override
//            public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
//                Employee employee = employeeRepository.getEmployeeByUsernameAndDeletedFalse(username)
//                        .orElseThrow(() -> new UsernameNotFoundException("Employee with username: " + username + " not found."));
//
//                return User.builder()
//                        .username(employee.getUsername())
//                        .roles(employee.getRole().name())
//                        .build();
//            }
//        };

        return username -> {
            Employee employee = employeeRepository.getEmployeeByUsernameAndDeletedFalse(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Employee with username: " + username + " not found."));

            return User.builder()
                    .username(employee.getUsername())
                    .roles(employee.getRole().name())
                    .build();
        };

    }
}
