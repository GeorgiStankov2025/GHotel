package org.ghotel.ghotel.common.security;

import org.ghotel.ghotel.entity.Employee;
import org.ghotel.ghotel.repository.EmployeeRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    public UserDetailsServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.getEmployeeByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new UsernameNotFoundException("Employee with username: " + username + " not found."));
        return User.builder()
                .username(employee.getUsername())
                .roles(employee.getRole().name())
                .build();

    }
}
